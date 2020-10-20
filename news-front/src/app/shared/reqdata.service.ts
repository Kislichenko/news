import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ReqData} from './interfaces';
import {environment} from '../../environments/environment';
import {map, tap} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class ReqdataService{
  constructor(
    private http: HttpClient
  ) {}

  create(reqData: ReqData):Observable<ReqData>{
    return this.http.post<ReqData>(`${environment.backendUrl}/requests`, reqData)
  }

  getAll(): Observable<ReqData[]>{
    return this.http.get(`${environment.backendUrl}/requests`)
      .pipe(
        tap(response => {
          console.log(response)
        }),
        map( (response: {[key: string]: any}) =>{
          console.log(response[1])
        return Object.keys(response).map(key => ({
          ...response[key],
          id: response[key].id
        }))
      }))
  }

  remove(id: string):Observable<void>{
    return this.http.delete<void>(`${environment.backendUrl}/requests/${id}`)
  }
}
