import { Component, OnInit } from '@angular/core';
import {AuthService} from '../shared/services/auth.service';

@Component({
  selector: 'app-advertiser-dashboard',
  templateUrl: './advertiser-dashboard.component.html',
  styleUrls: ['./advertiser-dashboard.component.scss']
})
export class AdvertiserDashboardComponent implements OnInit {

  constructor(
    private auth: AuthService
  ) { }

  ngOnInit(): void {
  }

  test(){
    console.log(this.auth.token)
  }

}
