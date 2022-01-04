import { Component, Input,OnInit } from '@angular/core';
import {Router,ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-project-header',
  templateUrl: './project-header.component.html',
  styleUrls: ['./project-header.component.css']
})

export class ProjectHeaderComponent implements OnInit {
  homepageurl = "homepage";
  ProjectCreatwpageurl = "createproject";
  ProjectOverviewpageurl = "choose-project";
  Logoutpageurl = "LoginPage";
  UserName:any;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.UserName = window.sessionStorage.getItem('Username');
  }

  redirectTo(url){
    this.router.navigateByUrl(url.toString());
  }
  NavitoProjectChoose(){
    this.redirectTo("choose-project");
  }
  NavitoCommitTrend(){
    this.redirectTo("commit-trend");
  }

  NavitoIssueTrack(){
    this.redirectTo("issue-track");
  }
  NavitoSonarQube(){
    this.redirectTo("sonar-qube");
  }
  NavitoLogout(){
    this.redirectTo("LoginPage");
    window.sessionStorage.clear();

  }
}
