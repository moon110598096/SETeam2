import {Component, OnInit} from '@angular/core';
// import {SonarQubeService} from './sonar-qube.service';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-sonar-qube-history',
  templateUrl: './sonar-qube-history.component.html',
  styleUrls: ['./sonar-qube-history.component.css']
})
export class SonarQubeHistoryComponent implements OnInit {
  datas : any;
    bugCount = -1;
    codeSmellsCount = -1;
    VulnerabilityCount = -1;
    component = "";
    bugsInfo = "";
    codeSmellInfo = "";
    vulnerabilityInfo = "";

    constructor(private router: Router) {

    }

    ngOnInit(): void {
  //     this.component = window.sessionStorage.getItem('repoName');
      this.component = "GitRepositoryAnalysisSystem";
      this.getSonarqubeInfo();
      this.getSonarQubeDetailInfo();
    }

    getSonarQubeDetailInfo(){

    }

    getSonarqubeInfo() {
//
//         const repoInfo = {
//           component: undefined,
//         };
//
//         repoInfo.component = this.component;
//
//         const data = JSON.stringify(repoInfo);
//
//         this.sonarQubeService.getSonarQubeCodeSmellsService(data).subscribe(
//           request => {
//             this.datas = request;
//             this.codeSmellsCount = this.datas.value;
//           }
//         );
//
//         this.sonarQubeService.getSonarQubeVulnerabilityService(data).subscribe(
//           request => {
//             this.datas = request;
//             this.VulnerabilityCount = this.datas.value;
//           }
//         );
//
//         this.sonarQubeService.getSonarQubeBugService(data).subscribe(
//           request => {
//             this.datas = request;
//             this.bugCount = this.datas.value;
//           }
//         );
    }

    goToSonarHistory(){
      this.router.navigate(['sonar-qube-history']);
    }
    goToSonarOverview(){
        this.router.navigate(['sonar-qube']);
    }
  }
