import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {ReqdataService} from '../shared/reqdata.service';
import {Observable} from 'rxjs';
import {ReqData} from '../shared/interfaces';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-request-page',
  templateUrl: './request-page.component.html',
  styleUrls: ['./request-page.component.scss']
})
export class RequestPageComponent implements OnInit {

  reqData$: Observable<ReqData>;

  constructor(
    private route: ActivatedRoute,
    private reqdataService: ReqdataService
  ) {
  }

  ngOnInit(): void {
    this.reqData$ = this.route.params
      .pipe(switchMap((params: Params) => {
        return this.reqdataService.getById(params['id']);
      }));
  }

}
