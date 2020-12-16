import {Component, OnInit} from '@angular/core';
import {News} from '../../shared/interfaces';
import {Subscription} from 'rxjs';
import {AuthService} from '../shared/services/auth.service';
import {AlertService} from '../shared/services/alert.service';
import {NewsService} from '../../shared/news.servive';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  news: News[] = [];
  pSub: Subscription;
  dSub: Subscription;
  uSub: Subscription;
  searchStr = '';

  constructor(
    private auth: AuthService,
    private newsService: NewsService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.pSub = this.newsService.getAll().subscribe(news => {
      this.news = news;
    });
  }

  ngOnDestroy(): void {
    //для того, чтобы не было утечек памяти, нужно очищать подписку
    if (this.pSub) {
      this.pSub.unsubscribe();
    }
    if (this.uSub) {
      this.uSub.unsubscribe();
    }
  }

  filterNewsByAvailability() {
    return this.news.filter(tmpNews => tmpNews.bad != true && tmpNews.realization != true);
  }

  block($event: MouseEvent, news: News) {
    this.uSub = this.newsService.update({
      ...news,
      bad: true
    }).subscribe(() => {
      this.alertService.success('Новость была заблокирована');
      this.ngOnInit();
    });
  }

  realization($event: MouseEvent, news: News) {
    this.uSub = this.newsService.update({
      ...news,
      infoManager: this.auth.username,
      realization: true
    }).subscribe(() => {
      this.alertService.success('Новость отправлена на реализацию');
      this.ngOnInit();
    });
  }
}
