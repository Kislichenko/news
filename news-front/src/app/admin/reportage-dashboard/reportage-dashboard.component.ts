import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../shared/services/auth.service';
import {Reportage, ReportageStatus} from '../../shared/interfaces';
import {Subscription} from 'rxjs';
import {AlertService} from '../shared/services/alert.service';
import {ReportageService} from '../../shared/reportage.service';

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
    private alertService: AlertService
  ) {
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

  //
  // signature($event: MouseEvent, reqData: ReqData) {
  //   this.uSub = this.reqdataService.update({
  //     ...reqData,
  //     signature: true
  //   }).subscribe(() => {
  //     this.alertService.success('Договор был подписан');
  //     this.ngOnInit();
  //   });
  // }


  public get reportageStatus(): typeof ReportageStatus {
    return ReportageStatus;
  }
}
