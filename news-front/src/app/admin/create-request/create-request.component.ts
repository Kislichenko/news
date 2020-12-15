import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AdBlockType, ReqData, Role} from '../../shared/interfaces';
import {ReqdataService} from '../../shared/reqdata.service';
import {AuthService} from '../shared/services/auth.service';
import {AlertService} from '../shared/services/alert.service';
import {compareNumbers} from '@angular/compiler-cli/src/diagnostics/typescript_version';

const adBlockType: Array<string> = Object.keys(AdBlockType).filter(key => isNaN(+key));

@Component({
  selector: 'app-create-request',
  templateUrl: './create-request.component.html',
  styleUrls: ['./create-request.component.scss']
})
export class CreateRequestComponent implements OnInit {

  form: FormGroup;
  ePropertyType = adBlockType;

  constructor(
    private reqdataService: ReqdataService,
    private auth: AuthService,
    private alert: AlertService
  ) {

  }

  datePickerConfig = {
    drops: 'up',
    format: 'YY/M/D'
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      subject: new FormControl(null, [Validators.required]),
      legalData: new FormControl(null, [Validators.required]),
      wishes: new FormControl(null, [Validators.required]),
      startDate: new FormControl(null, [Validators.required]),
      endDate: new FormControl(null, [Validators.required]),
      type: new FormControl(null, [Validators.required])
    })
  }

  submit(){
    if(this.form.invalid){
      return
    }

    const reqData: ReqData ={
      subject: this.form.value.subject,
      startDate: this.form.value.startDate._d,
      endDate: this.form.value.endDate._d,
      legalData: this.form.value.legalData,
      creationDate: new Date(),
      type: this.form.value.type,
      creator: this.auth.username,
      wishes: this.form.value.wishes,
      payed: false,
      confirm: false,
      contract: false,
      signature: false,
      cost: 0
    }
    this.reqdataService.create(reqData).subscribe(()=>{
      this.form.reset()
      this.alert.success('Заявка была создана!')
    })
  }

}
