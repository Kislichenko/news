import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../shared/services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../../shared/interfaces';

@Component({
  selector: 'app-reg-page',
  templateUrl: './reg-page.component.html',
  styleUrls: ['./reg-page.component.scss']
})
export class RegPageComponent implements OnInit {

  form: FormGroup;
  submitted = false;
  message: string;

  constructor(
    public auth: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl(null, [
        Validators.required
      ]),
      email: new FormControl(null, [
        Validators.required,
        Validators.email
      ]),
      name: new FormControl(null, [
        Validators.required
      ]),
      surname: new FormControl(null, [
        Validators.required
      ]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(6)
      ]),
      confirmPassword: new FormControl(null, [
        Validators.required,
        Validators.minLength(6)
      ])
    }, {
      validators: this.password.bind(this)
    });
  }

  password(formGroup: FormGroup) {
    const {value: password} = formGroup.get('password');
    const {value: confirmPassword} = formGroup.get('confirmPassword');
    return password === confirmPassword ? null : {passwordNotMatch: true};
  }

  submit() {
    if (this.form.invalid) {
      return;
    }

    this.submitted = true;

    const user: User = {
      username: this.form.value.username,
      password: this.form.value.password,
      email: this.form.value.email,
      name: this.form.value.name,
      surname: this.form.value.surname,
      confirmPassword: this.form.value.confirmPassword
    };

    this.auth.register(user).subscribe(() => {
      this.form.reset();
      this.router.navigate(['/cabinet', 'login']);
      this.submitted = false;
    }, () => {
      this.submitted = false;
    });
  }

}

