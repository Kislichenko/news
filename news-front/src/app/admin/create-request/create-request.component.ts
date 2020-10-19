import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ReqData} from '../../shared/interfaces';
import {ReqdataService} from '../../shared/reqdata.service';

@Component({
  selector: 'app-create-request',
  templateUrl: './create-request.component.html',
  styleUrls: ['./create-request.component.scss']
})
export class CreateRequestComponent implements OnInit {

  form: FormGroup;

  constructor(
    private reqdataService: ReqdataService
  ) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      subject: new FormControl(null, [
        Validators.required
      ]),
      duration: new FormControl(null, [
        Validators.required
      ]),
      legaldata: new FormControl(null, [
        Validators.required
      ]),
      wishes: new FormControl(null, [
        Validators.required
      ]),

    })
  }

  submit(){
    if(this.form.invalid){
      return
    }

    const reqData: ReqData ={
      subject: this.form.value.subject,
      duration: this.form.value.duration,
      legaldata: this.form.value.legaldata,
      date: new Date()
    }
    this.reqdataService.create(reqData).subscribe(()=>{
      this.form.reset()
    })

    console.log(reqData)
  }

}
