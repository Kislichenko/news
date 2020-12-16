import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {Role} from '../../../../shared/interfaces';

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.scss']
})
export class AdminLayoutComponent implements OnInit {

  constructor(
    private router: Router,
    public auth: AuthService) {
  }

  public get role(): typeof Role {
    return Role;
  }

  ngOnInit(): void {
  }

  logout(event: Event) {
    //отмена дефолтного поведения ссылки
    event.preventDefault();
    this.auth.logout();
    this.router.navigate(['/cabinet', 'login']);
  }

}
