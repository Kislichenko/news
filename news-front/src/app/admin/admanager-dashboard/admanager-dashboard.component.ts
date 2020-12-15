import {Component, OnDestroy, OnInit} from '@angular/core';
import {ReqData} from '../../shared/interfaces';
import {Subscription} from 'rxjs';
import {AuthService} from '../shared/services/auth.service';
import {ReqdataService} from '../../shared/reqdata.service';
import {AlertService} from '../shared/services/alert.service';

@Component({
  selector: 'app-admanager-dashboard',
  templateUrl: './admanager-dashboard.component.html',
  styleUrls: ['./admanager-dashboard.component.scss']
})
export class AdmanagerDashboardComponent implements OnInit, OnDestroy{

  reqDatas: ReqData[] = []
  pSub: Subscription
  dSub: Subscription
  uSub: Subscription
  searchStr = ''

  constructor(
    private auth: AuthService,
    private reqdataService: ReqdataService,
    private alertService: AlertService
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
    if(this.dSub){
      this.dSub.unsubscribe()
    }
    if(this.uSub){
      this.uSub.unsubscribe()
    }
  }

  remove(id: string){
    this.dSub = this.reqdataService.remove(id).subscribe(() => {
      this.reqDatas = this.reqDatas.filter(reqData => reqData.id !== id)
      this.alertService.warning('Пост был удален!')
    })
  }


  buy($event: MouseEvent, reqData: ReqData) {
    this.uSub = this.reqdataService.update({
      ...reqData,
      payed: true
    }).subscribe(() => {
      this.alertService.success('Блок был оплачен')
      this.ngOnInit();
    })
  }
}

