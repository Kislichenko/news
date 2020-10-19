import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {FbAuthResponse, Role, User} from '../../../shared/interfaces';
import {Observable, Subject, throwError} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {catchError, map, tap} from 'rxjs/operators';
import jwt_decode from "jwt-decode";

@Injectable()
export class AuthService{

  public error$: Subject<string> = new Subject<string>()

  constructor(private http: HttpClient) {
  }

  get role(): string{
    return localStorage.getItem('role')
  }

  get username(): string{
    return localStorage.getItem('username')
  }

  get token(): string{
    const expDate = new Date(localStorage.getItem('fb-token-exp'))
    if (new Date() > expDate){
      this.logout()
      return null
    }
    return localStorage.getItem('fb-token')
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  register(user: User): Observable<any>{
    const tmp = this.http.post<HttpResponse<any>>(`http://localhost:8080/sign-up`,user, {observe: 'response'})
      .pipe(tap(response =>{
          console.log("Registration")
          console.log(response)
        }),
        catchError(this.hadleError.bind(this)))

    return tmp
  }

  login(user: User): Observable<any>{
    //user.returnSecureToken = true
    const tmp = this.http.post<HttpResponse<any>>(`http://localhost:8080/login`,user, {observe: 'response'})
    .pipe(tap(response =>{
      this.setToken(response)
      console.log(response.headers.get("authorization"))
    }),
      catchError(this.hadleError.bind(this)))

    return tmp

      // .pipe(
      //   tap(this.setToken),
      //   catchError(this.hadleError.bind(this))
      // )
  }

  logout(){
    this.setToken(null)
  }

  isAuthenticated(): boolean{
    return !!this.token
  }

  checkRole(roleName: string): boolean{
    if (this.role.includes(roleName)){
      return true
    }else{
      return false
    }
  }

  private hadleError(error: HttpErrorResponse){
    console.log(error)
    var message = error.error.error
    // const {message} = error.error.error

    switch (message) {
      case 'USERNAME_ALREADY_USED':
        console.log(error.error.error)
        this.error$.next('Такой логин уже используется. Выберите другой!')
        break
      case 'BAD_CREDENTIALS':
        this.error$.next('Неверный логин/пароль')
        break
    }

    return throwError(error)

  }

  private setToken(response: HttpResponse<any> | null){
    if (response) {
      const decoded = jwt_decode(response.headers.get("authorization"))
      const expDate = new Date( +decoded.exp * 1000)
      localStorage.setItem('fb-token', response.headers.get("authorization"))
      localStorage.setItem('fb-token-exp', expDate.toString())
      localStorage.setItem('username', decoded.sub)
      localStorage.setItem('role', decoded.roles)
    }else{
      localStorage.clear()
    }
  }

}
