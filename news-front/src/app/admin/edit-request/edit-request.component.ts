import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {ReqdataService} from '../../shared/reqdata.service';
import {switchMap} from 'rxjs/operators';
import {AdBlockType, ReqData} from '../../shared/interfaces';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {DatePipe} from '@angular/common';
import {Subscription} from 'rxjs';
import {AlertService} from '../shared/services/alert.service';
import * as moment from 'jalali-moment';

const adBlockType: Array<string> = Object.keys(AdBlockType).filter(key => isNaN(+key));

@Component({
  selector: 'app-edit-request',
  templateUrl: './edit-request.component.html',
  styleUrls: ['./edit-request.component.scss']
})
export class EditRequestComponent implements OnInit, OnDestroy {

  form: FormGroup;
  ePropertyType = adBlockType;
  reqData: ReqData;
  submitted = false;

  uSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private reqdataService: ReqdataService,
    private datepipe: DatePipe,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.route.params.pipe(
      switchMap((params: Params) => {
        return this.reqdataService.getById(params['id']);
      })
    ).subscribe((reqData: ReqData) => {
      this.reqData = reqData;
      this.form = new FormGroup({
        subject: new FormControl(reqData.subject, [Validators.required]),
        legalData: new FormControl(reqData.legalData, [Validators.required]),
        wishes: new FormControl(reqData.wishes, [Validators.required]),
        startDate: new FormControl(this.datepipe.transform(reqData.startDate, 'dd-MM-yyyy'), [Validators.required]),
        endDate: new FormControl(this.datepipe.transform(reqData.endDate, 'dd-MM-yyyy'), [Validators.required]),
        type: new FormControl(reqData.type, [Validators.required])
      });
    });
  }

  public buy(event) {
    this.reqData.payed = true;
    this.updateRequest('Блок был оплачен!');
  }

  submit() {
    if (this.form.invalid) {
      return;
    }
    this.updateRequest('Заявка была обновлена!');
  }

  ngOnDestroy(): void {
    if (this.uSub) {
      this.uSub.unsubscribe();
    }
  }

  private updateRequest(infoText: string) {
    this.submitted = true;

    this.form.controls['startDate'].setValue(moment.from(this.form.value.startDate, 'ru', 'DD-MM-YYYY'));
    this.form.controls['endDate'].setValue(moment.from(this.form.value.endDate, 'ru', 'DD-MM-YYYY'));

    this.uSub = this.reqdataService.update({
      ...this.reqData,
      id: this.reqData.id,
      subject: this.form.value.subject,
      startDate: new Date(this.form.value.startDate),
      endDate: new Date(Date.parse(this.form.value.endDate)),
      legalData: this.form.value.legalData,
      type: this.form.value.type,
      wishes: this.form.value.wishes,
      payed: this.reqData.payed,
      contract: this.reqData.contract,
      signature: this.reqData.signature,
      cost: this.reqData.cost
    }).subscribe(() => {
      this.submitted = false;
      this.alertService.success(infoText);
    });
  }
}
