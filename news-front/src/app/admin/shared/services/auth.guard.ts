import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';

@Injectable()
export class AuthGuard implements CanActivate{

  constructor(
    private auth: AuthService,
    private router: Router
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let url: string = state.url;
    return this.checkUserLogin(route, url);
    // if (this.auth.isAuthenticated()){
    //   return true
    // }else{
    //   this.auth.logout()
    //   this.router.navigate(['/admin', 'login'], {
    //     queryParams:{
    //       loginAgain: true
    //     }
    //   })
    // }
  }

  checkUserLogin(route: ActivatedRouteSnapshot, url: any): boolean {
    if (this.auth.isAuthenticated()) {
      console.log(this.auth.role)
      console.log(route.data.role.indexOf(this.auth.role))
      console.log(route.data.role)
      const userRole = this.auth.role;
      if (route.data.role && route.data.role.indexOf(userRole) === -1) {
        this.auth.logout()
        this.router.navigate(['/cabinet', 'login'], {
          queryParams:{
            loginAgain: true
          }
        })
        return false;
      }
      return true;
    }

    this.router.navigate(['/home']);
    return false;
  }

}
