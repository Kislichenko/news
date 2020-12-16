import {Component, OnDestroy, OnInit} from '@angular/core';
import {ReqData, Role, User} from '../../shared/interfaces';
import {Subscription} from 'rxjs';
import {AuthService} from '../shared/services/auth.service';
import {ReqdataService} from '../../shared/reqdata.service';
import {AlertService} from '../shared/services/alert.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Params} from '@angular/router';
import {DatePipe} from '@angular/common';
import {switchMap} from 'rxjs/operators';
import * as moment from 'jalali-moment';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User

  constructor(
    private route: ActivatedRoute,
    private auth: AuthService,
  ) { }

  ngOnInit(): void {
    this.auth.getUserByUserName(this.auth.username).subscribe(user => {
      console.log(user)
      this.user = user;
    });
  }
}
