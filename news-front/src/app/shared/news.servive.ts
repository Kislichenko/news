import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {News} from './interfaces';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class NewsService {
  constructor(
    private http: HttpClient
  ) {
  }

  getAll(): Observable<News[]> {
    return this.http.get(`${environment.backendUrl}/news`)
      .pipe(map((response: { [key: string]: any }) => {
        return Object.keys(response).map(key => ({
          ...response[key],
          id: response[key].id
        }));
      }));
  }

  getById(id: string): Observable<News> {
    return this.http.get<News>(`${environment.backendUrl}/news/${id}`)
      .pipe(map((news: News) => {
        return {
          ...news, id
        };
      }));
  }

  update(news: News): Observable<News> {
    return this.http.patch<News>(`${environment.backendUrl}/news/${news.id}`, news);
  }
}
