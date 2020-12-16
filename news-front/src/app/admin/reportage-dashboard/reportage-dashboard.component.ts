import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../shared/services/auth.service';
import {Reportage, ReportageStatus, Role} from '../../shared/interfaces';
import {Subscription} from 'rxjs';
import {AlertService} from '../shared/services/alert.service';
import {ReportageService} from '../../shared/reportage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reportage-dashboard',
  templateUrl: './reportage-dashboard.component.html',
  styleUrls: ['./reportage-dashboard.component.scss']
})
export class ReportageDashboardComponent implements OnInit, OnDestroy {

  reports: Reportage[] = [];
  pSub: Subscription;
  dSub: Subscription;
  uSub: Subscription;
  searchStr = '';

  constructor(
    private auth: AuthService,
    private reportageService: ReportageService,
    private alertService: AlertService,
    private router: Router
  ) {
  }

  filterReportsOfUser(){
    if (this.router.url === '/cabinet/reporter/dashboard' && this.auth.checkRole(this.role.Reporter)){
      return this.reports.filter(reportage => reportage.reporter == this.auth.username && reportage.publish == false)
    }else {
      return this.reports.filter(reportage => (reportage.status == this.reportageStatus.Created || reportage.confirm == true)
        && reportage.publish == false)
    }

  }

  ngOnInit(): void {
    this.pSub = this.reportageService.getAll().subscribe(reports => {
      this.reports = reports;
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

  remove(id: string) {
    this.dSub = this.reportageService.remove(id).subscribe(() => {
      this.reports = this.reports.filter(reportage => reportage.id !== id);
      this.alertService.warning('Пост был удален!');
    });
  }


  lock($event: MouseEvent, reportage: Reportage) {
    this.uSub = this.reportageService.update({
      ...reportage,
      status: this.reportageStatus.Inwork,
      reporter: this.auth.username
    }).subscribe(() => {
      this.alertService.success('Репортаж был заблокирован');
      this.ngOnInit();
    });
  }

  sendCheck($event: MouseEvent, reportage: Reportage) {
    this.uSub = this.reportageService.update({
      ...reportage,
      confirm: true
    }).subscribe(() => {
      this.alertService.success('Репортаж был отправлен на проверку');
      this.ngOnInit();
    });
  }

  remake($event: MouseEvent, reportage: Reportage) {
    this.uSub = this.reportageService.update({
      ...reportage,
      confirm: false
    }).subscribe(() => {
      this.alertService.success('Репортаж был отправлен на переделку');
      this.ngOnInit();
    });
  }

  publish($event: MouseEvent, reportage: Reportage) {
    this.uSub = this.reportageService.update({
      ...reportage,
      publish: true
    }).subscribe(() => {
      this.alertService.success('Репортаж был опубликован');
      this.ngOnInit();
    });
  }

  unlock($event: MouseEvent, reportage: Reportage) {
    this.uSub = this.reportageService.update({
      ...reportage,
      status: this.reportageStatus.Created,
      reporter: ""
    }).subscribe(() => {
      this.alertService.success('Репортаж был разблокирован');
      this.ngOnInit();
    });
  }


  public get reportageStatus(): typeof ReportageStatus {
    return ReportageStatus;
  }

  public get role(): typeof Role {
    return Role;
  }
}
