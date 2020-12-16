import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../shared/services/auth.service';
import {ReqdataService} from '../../shared/reqdata.service';
import {ReqData, Role} from '../../shared/interfaces';
import {Subscription} from 'rxjs';
import {AlertService} from '../shared/services/alert.service';

@Component({
  selector: 'app-req-dashboard',
  templateUrl: './req-dashboard.component.html',
  styleUrls: ['./req-dashboard.component.scss']
})
export class ReqDashboardComponent implements OnInit, OnDestroy {

  reqDatas: ReqData[] = [];
  pSub: Subscription;
  dSub: Subscription;
  uSub: Subscription;
  searchStr = '';

  constructor(
    private auth: AuthService,
    private reqdataService: ReqdataService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.pSub = this.reqdataService.getAll().subscribe(reqDatas => {
      this.reqDatas = reqDatas;
    });
  }

  ngOnDestroy(): void {
    //для того, чтобы не было утечек памяти, нужно очищать подписку
    if (this.pSub) {
      this.pSub.unsubscribe();
    }
    if (this.dSub) {
      this.dSub.unsubscribe();
    }
    if (this.uSub) {
      this.uSub.unsubscribe();
    }
  }

  filterItemsOfType(username){
    return this.reqDatas.filter(reqData => reqData.creator == username || this.auth.checkRole(this.role.AdManager));
  }

  remove(id: string) {
    this.dSub = this.reqdataService.remove(id).subscribe(() => {
      this.reqDatas = this.reqDatas.filter(reqData => reqData.id !== id);
      this.alertService.warning('Пост был удален!');
    });
  }


  buy($event: MouseEvent, reqData: ReqData) {
    this.uSub = this.reqdataService.update({
      ...reqData,
      payed: true
    }).subscribe(() => {
      this.alertService.success('Блок был оплачен');
      this.ngOnInit();
    });
  }

  contract($event: MouseEvent, reqData: ReqData) {
    this.uSub = this.reqdataService.update({
      ...reqData,
      contract: true
    }).subscribe(() => {
      this.alertService.success('Договор был сформирован');
      this.ngOnInit();
    });
  }

  signature($event: MouseEvent, reqData: ReqData) {
    this.uSub = this.reqdataService.update({
      ...reqData,
      signature: true
    }).subscribe(() => {
      this.alertService.success('Договор был подписан');
      this.ngOnInit();
    });
  }

  confirm($event: MouseEvent, reqData: ReqData) {
    this.uSub = this.reqdataService.update({
      ...reqData,
      confirm: true
    }).subscribe(() => {
      this.alertService.success('Заявка на рекламу была подтверждена');
      this.ngOnInit();
    });
  }

  public get role(): typeof Role {
    return Role;
  }
}
