import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {FbAuthResponse, Role, User} from '../../../shared/interfaces';
import {Observable, Subject, throwError} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {catchError, map, tap} from 'rxjs/operators';

@Injectable()
export class AuthService{

  public error$: Subject<string> = new Subject<string>()

  constructor(private http: HttpClient) {
  }

  get role(): string{
    return localStorage.getItem('role')
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

  login(user: User): Observable<any>{
    //user.returnSecureToken = true
    const tmp = this.http.post<HttpResponse<any>>(`http://localhost:8080/login`,user, {observe: 'response'})
    .pipe(tap(response =>{
      this.setToken(response)
      console.log("TTTTT")
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

  checkRole(roleName: String): boolean{
    if (this.role == roleName){
      return true
    }else{
      return false
    }
  }

  private hadleError(error: HttpErrorResponse){
    const {message} = error.error.error

    switch (message) {
      case 'EMAIL_NOT_FOUND':
        this.error$.next('Такого email нет')
        break
      case 'INVALID_EMAIL':
        this.error$.next('Неверный email')
        break
      case 'INVALID_PASSWORD':
        this.error$.next('Неверный пароль')
        break

    }

    return throwError(error)

  }

  private setToken(response: HttpResponse<any> | null){
    console.log("AAAA")
    console.log(response)
    console.log("AAAA2")
    if (response) {
      const expDate = new Date(new Date().getTime() + +36000000 * 1000)//исправить время
      localStorage.setItem('fb-token', response.headers.get("authorization"))
      localStorage.setItem('fb-token-exp', expDate.toString())
      localStorage.setItem('role', Role.User)//заглушка, тоже надо исправить
    }else{
      localStorage.clear()
    }
  }

}
