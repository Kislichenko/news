import {Component, Input, OnInit} from '@angular/core';
import {ReqData} from '../../interfaces';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  @Input() reqData: ReqData

  constructor() { }

  ngOnInit(): void {
  }

}
