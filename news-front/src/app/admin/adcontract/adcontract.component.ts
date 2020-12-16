import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AdBlockType, ReqData} from '../../shared/interfaces';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Params} from '@angular/router';
import {ReqdataService} from '../../shared/reqdata.service';
import {DatePipe} from '@angular/common';
import {AlertService} from '../shared/services/alert.service';
import {switchMap} from 'rxjs/operators';

const adBlockType: Array<string> = Object.keys(AdBlockType).filter(key => isNaN(+key));

@Component({
  selector: 'app-adcontract',
  templateUrl: './adcontract.component.html',
  styleUrls: ['./adcontract.component.scss']
})
export class AdcontractComponent implements OnInit {

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

}

