import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {AuthService} from '../admin/shared/services/auth.service';
import {Router} from '@angular/router';
import {catchError, tap} from 'rxjs/operators';

@Injectable()
export class AuthInteceptor implements HttpInterceptor{
  constructor(
    private auth: AuthService,
    private router: Router
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(this.auth.isAuthenticated()){
      const headers = new HttpHeaders({
        'Authorization': this.auth.token,
        'Content-Type': 'application/json'
      });

      req = req.clone({headers})
    }

    return next.handle(req)
      .pipe(
        tap(() => {
          console.log('Intercept')
        }),
        catchError((error: HttpErrorResponse) => {
          console.log('[Interceptor Error]: ', error)
          if(error.status === 401){
            this.auth.logout()
            this.router.navigate(['/cabinet', 'login'], {
              queryParams:{
                authFailed: true
              }
            })
          }
          return throwError(error)
        })
      )
  }

}
