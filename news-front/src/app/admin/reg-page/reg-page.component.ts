import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../shared/services/auth.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {User} from '../../shared/interfaces';

@Component({
  selector: 'app-reg-page',
  templateUrl: './reg-page.component.html',
  styleUrls: ['./reg-page.component.scss']
})
export class RegPageComponent implements OnInit {

  form: FormGroup
  submitted = false
  message: string

  constructor(
    public auth: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      email: new FormControl(null, [
        Validators.required,
        Validators.email
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
      username: this.form.value.email,
      password: this.form.value.password
    }

    this.auth.register(user).subscribe(()=>{
      this.form.reset()
      this.router.navigate(['/admin', 'login'])
      this.submitted = false
    }, () => {
      this.submitted = false
    })
  }

}

