import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ReqData} from './interfaces';
import {environment} from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class ReqdataService{
  constructor(
    private http: HttpClient
  ) {}

  create(reqData: ReqData):Observable<ReqData>{
    return this.http.post<ReqData>(`${environment.backendUrl}/requests`, reqData)
  }
}
