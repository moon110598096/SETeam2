import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OauthService {

  constructor(private httpClient: HttpClient) {}

  public authorize(body){
      const headers = new HttpHeaders({
            'Content-Type': 'text/json'
      });
      const options = {
        headers
      };
      console.log("test");

      console.log("options: ",options);
      return this.httpClient.post<any>('/GitRepositoryAnalysisSystem/OAuthorize', body, options);
      console.log("post done");
    }
}
