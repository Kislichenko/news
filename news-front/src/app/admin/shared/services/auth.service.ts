import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {User} from '../../../shared/interfaces';
import {Observable, Subject, throwError} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {catchError, map, tap} from 'rxjs/operators';
import jwt_decode from 'jwt-decode';

@Injectable({providedIn: 'root'})
export class AuthService {

  public error$: Subject<string> = new Subject<string>();
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
  }

  get role(): string {
    return localStorage.getItem('role');
  }

  get username(): string {
    return localStorage.getItem('username');
  }

  get token(): string {
    const expDate = new Date(localStorage.getItem('fb-token-exp'));
    if (new Date() > expDate) {
      this.logout();
      return null;
    }
    return localStorage.getItem('fb-token');
  }

  register(user: User): Observable<any> {
    const tmp = this.http.post<HttpResponse<any>>(`${environment.backendUrl}/sign-up`, user, {observe: 'response'})
      .pipe(tap(response => {
          console.log('Registration');
          // console.log(response);
        }),
        catchError(this.hadleError.bind(this)));

    return tmp;
  }

  login(user: User): Observable<any> {
    const tmp = this.http.post<HttpResponse<any>>(`${environment.backendUrl}/login`, user, {observe: 'response'})
      .pipe(tap(response => {
          this.setToken(response);
          console.log(response.headers.get('authorization'));
        }),
        catchError(this.hadleError.bind(this)));

    return tmp;
  }


  getUserByUserName(username: string): Observable<User> {
    return this.http.get<User>(`${environment.backendUrl}/users/${username}`)
      .pipe(map((user: User) => {
        return {
          ...user
        };
      }));
  }


  logout() {
    this.setToken(null);
  }

  isAuthenticated(): boolean {
    return !!this.token;
  }

  checkRole(roleName: string): boolean {
    if (this.role.includes(roleName)) {
      return true;
    } else {
      return false;
    }
  }

  private hadleError(error: HttpErrorResponse) {
    console.log(error);
    var message = error.error.error;
    // const {message} = error.error.error

    switch (message) {
      case 'USERNAME_ALREADY_USED':
        console.log(error.error.error);
        this.error$.next('Такой логин уже используется. Выберите другой!');
        break;
      case 'BAD_CREDENTIALS':
        this.error$.next('Неверный логин/пароль');
        break;
    }

    return throwError(error);

  }

  private setToken(response: HttpResponse<any> | null) {
    if (response) {
      const decoded = jwt_decode(response.headers.get('authorization'));
      const expDate = new Date(+decoded.exp * 1000);
      localStorage.setItem('fb-token', response.headers.get('authorization'));
      localStorage.setItem('fb-token-exp', expDate.toString());
      localStorage.setItem('username', decoded.sub);
      localStorage.setItem('role', decoded.roles);
    } else {
      localStorage.clear();
    }
  }

}
