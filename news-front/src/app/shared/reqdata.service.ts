import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ReqData} from './interfaces';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class ReqdataService {
  constructor(
    private http: HttpClient
  ) {
  }

  create(reqData: ReqData): Observable<ReqData> {
    return this.http.post<ReqData>(`${environment.backendUrl}/requests`, reqData);
  }

  getAll(): Observable<ReqData[]> {
    return this.http.get(`${environment.backendUrl}/requests`)
      .pipe(map((response: { [key: string]: any }) => {
        return Object.keys(response).map(key => ({
          ...response[key],
          id: response[key].id
        }));
      }));
  }

  getById(id: string): Observable<ReqData> {
    return this.http.get<ReqData>(`${environment.backendUrl}/requests/${id}`)
      .pipe(map((reqData: ReqData) => {
        return {
          ...reqData, id
        };
      }));
  }

  remove(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.backendUrl}/requests/${id}`);
  }

  update(reqData: ReqData): Observable<ReqData> {
    return this.http.patch<ReqData>(`${environment.backendUrl}/requests/${reqData.id}`, reqData);
  }
}
