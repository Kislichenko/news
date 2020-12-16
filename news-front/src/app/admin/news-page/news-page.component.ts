import {Component, OnInit} from '@angular/core';
import {switchMap} from 'rxjs/operators';
import {ActivatedRoute, Params} from '@angular/router';
import {News} from '../../shared/interfaces';
import {NewsService} from '../../shared/news.servive';

@Component({
  selector: 'app-news-page',
  templateUrl: './news-page.component.html',
  styleUrls: ['./news-page.component.scss']
})
export class NewsPageComponent implements OnInit {

  news: News;

  constructor(
    private route: ActivatedRoute,
    private newsServise: NewsService
  ) {
  }

  ngOnInit(): void {
    this.route.params.pipe(
      switchMap((params: Params) => {
        return this.newsServise.getById(params['id']);
      })
    ).subscribe((news: News) => {
      this.news = news;
    });
  }


}
