import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Role, User} from '../../shared/interfaces';
import {AuthService} from '../shared/services/auth.service';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  form: FormGroup
  submitted = false
  message: string

  constructor(
    public auth: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params: Params)=>{
      if (params['loginAgain']){
        this.message = "Авторизуйтесь!"
      }
    })

    this.form = new FormGroup({
      username: new FormControl(null, [
        Validators.required
      ]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(6)
      ]),
    })
  }

  submit(){
    if (this.form.invalid){
      return
    }

    this.submitted = true

    const user: User = {
      username: this.form.value.username,
      password: this.form.value.password
    }

    this.auth.login(user).subscribe(()=>{
      this.form.reset()
      if(this.auth.role.includes(Role.User)){
        this.router.navigate(['/admin', this.auth.username , 'dashboard'])
      }else if(this.auth.role.includes(Role.AdManager)){
        this.router.navigate(['/admin', this.auth.username,'dashboard'])
      }
      this.submitted = false
    }, () => {
      this.submitted = false
    })
  }

}
