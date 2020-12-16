import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Reportage, ReqData} from './interfaces';
import {environment} from '../../environments/environment';
import {map, tap} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class ReportageService{
  constructor(
    private http: HttpClient
  ) {}

  create(reportage: Reportage):Observable<Reportage>{
    return this.http.post<Reportage>(`${environment.backendUrl}/reportages`, reportage)
  }

  getAll(): Observable<Reportage[]>{
    return this.http.get(`${environment.backendUrl}/reportages`)
      .pipe(
        map( (response: {[key: string]: any}) =>{
        return Object.keys(response).map(key => ({
          ...response[key],
          id: response[key].id
        }))
      }))
  }

  getById(id: string): Observable<Reportage>{
    return this.http.get<Reportage>(`${environment.backendUrl}/reportages/${id}`)
      .pipe(map( (reportage: Reportage) =>{
        return {
          ...reportage, id
        }
      }))
  }

  remove(id: string):Observable<void>{
    return this.http.delete<void>(`${environment.backendUrl}/reportages/${id}`)
  }

  update(reportage: Reportage): Observable<Reportage>{
    return this.http.patch<Reportage>(`${environment.backendUrl}/reportages/${reportage.id}`, reportage)
  }
}
