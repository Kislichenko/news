import {Component, OnInit} from '@angular/core';
import {User} from '../../shared/interfaces';
import {AuthService} from '../shared/services/auth.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User;

  constructor(
    private route: ActivatedRoute,
    private auth: AuthService,
  ) {
  }

  ngOnInit(): void {
    this.auth.getUserByUserName(this.auth.username).subscribe(user => {
      // console.log(user);
      this.user = user;
    });
  }
}
