import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {OauthService} from './oauth.service';

@Component({
  selector: 'app-oauth',
  templateUrl: './oauth.component.html',
  styleUrls: ['./oauth.component.css']
})
export class OauthComponent implements OnInit {

  constructor(private router: Router, private acRouter: ActivatedRoute, private  oAuthService: OauthService) { }

  ngOnInit(): void {
    this.acRouter.queryParams.subscribe(params => {
      console.log("params: ",params);
      const grant = params.code;
//         this.oAuthService.authorize(grant);
      const callBackData = {
            code: grant,
          };
      const data = JSON.stringify(callBackData);

      this.oAuthService.authorize(data).subscribe(
        request => {
          this.router.navigateByUrl(request.redirect.toString());
          sessionStorage.setItem('Username', request.name);
          sessionStorage.setItem('UserID', request.id);
        });
    });
  }

}
