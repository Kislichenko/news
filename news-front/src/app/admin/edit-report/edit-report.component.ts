import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Reportage, Role} from '../../shared/interfaces';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Params} from '@angular/router';
import {DatePipe} from '@angular/common';
import {AlertService} from '../shared/services/alert.service';
import {switchMap} from 'rxjs/operators';
import {ReportageService} from '../../shared/reportage.service';
import {AuthService} from '../shared/services/auth.service';

@Component({
  selector: 'app-edit-report',
  templateUrl: './edit-report.component.html',
  styleUrls: ['./edit-report.component.scss']
})
export class EditReportComponent implements OnInit, OnDestroy {

  form: FormGroup;
  reportage: Reportage;
  submitted = false;

  uSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private reportageService: ReportageService,
    private auth: AuthService,
    private datepipe: DatePipe,
    private alertService: AlertService
  ) {
  }

  public get role(): typeof Role {
    return Role;
  }

  ngOnInit(): void {
    this.route.params.pipe(
      switchMap((params: Params) => {
        return this.reportageService.getById(params['id']);
      })
    ).subscribe((reportage: Reportage) => {
      this.reportage = reportage;
      this.form = new FormGroup({
        text: new FormControl(reportage.text, [Validators.required]),
        comment: new FormControl(reportage.comment),
      });
    });
  }

  submit() {
    if (this.form.invalid) {
      return;
    }
    this.updateReportage('Репортаж был обновлен!');
  }

  ngOnDestroy(): void {
    if (this.uSub) {
      this.uSub.unsubscribe();
    }
  }

  private updateReportage(infoText: string) {
    this.submitted = true;

    this.uSub = this.reportageService.update({
      ...this.reportage,
      id: this.reportage.id,
      text: this.form.value.text,
      comment: this.form.value.comment,
      infoManager: this.reportage.infoManager,
      reporter: this.reportage.reporter,
      startDate: this.reportage.startDate,
      endDate: this.reportage.endDate,
      confirm: this.reportage.confirm,
      status: this.reportage.status,
      news: this.reportage.news,
      newsArticle: this.reportage.newsArticle
    }).subscribe(() => {
      this.submitted = false;
      this.alertService.success(infoText);
    });
  }
}
