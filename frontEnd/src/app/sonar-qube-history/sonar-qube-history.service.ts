import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SonarQubeHistoryService {

  constructor(private httpClient: HttpClient) {}

  // tslint:disable-next-line:typedef
  public getSonarQubeHistoryService(body) {
    const headers = new HttpHeaders({
      'Content-Type': 'text/json'
    });
    const options = {
      headers
    };
    return this.httpClient.post<any>('/GitRepositoryAnalysisSystem/SonarHistory', body, options);
  }
 }
