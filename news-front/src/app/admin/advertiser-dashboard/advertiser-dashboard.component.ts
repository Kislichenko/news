import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../shared/services/auth.service';
import {ReqdataService} from '../../shared/reqdata.service';
import {ReqData} from '../../shared/interfaces';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-advertiser-dashboard',
  templateUrl: './advertiser-dashboard.component.html',
  styleUrls: ['./advertiser-dashboard.component.scss']
})
export class AdvertiserDashboardComponent implements OnInit, OnDestroy {

  reqDatas: ReqData[] = []
  pSub: Subscription
  searchStr = ''

  constructor(
    private auth: AuthService,
    private reqdataService: ReqdataService
  ) { }

  ngOnInit(): void {
    this.pSub = this.reqdataService.getAll().subscribe(reqDatas => {
      this.reqDatas = reqDatas
    })
  }

  ngOnDestroy(): void {
    //для того, чтобы не было утечек памяти, нужно очищать подписку
    if(this.pSub){
      this.pSub.unsubscribe()
    }
  }

  remove(id: string){

  }

}
