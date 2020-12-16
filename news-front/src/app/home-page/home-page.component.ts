import {Component, OnInit} from '@angular/core';
import {ReqdataService} from '../shared/reqdata.service';
import {Observable} from 'rxjs';
import {ReqData} from '../shared/interfaces';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  reqDatas$: Observable<ReqData[]>;

  constructor(
    private reqdataService: ReqdataService
  ) {
  }

  ngOnInit(): void {
    this.reqDatas$ = this.reqdataService.getAll();
  }

}
