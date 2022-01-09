import {Component, OnInit} from '@angular/core';
import {SonarQubeService} from './sonar-qube.service';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-sonar-qube',
  templateUrl: './sonar-qube.component.html',
  styleUrls: ['./sonar-qube.component.css']
})

export class SonarQubeComponent implements OnInit {
  datas : any;
  issueDatas : any;
  bugCount = 0;
  codeSmellsCount = 0;
  VulnerabilityCount = 0;
  component = "";
  bugsInfo = [];
  codeSmellInfo = [];
  vulnerabilityInfo = [];

  constructor(private router: Router,private sonarQubeService: SonarQubeService) {

  }

  ngOnInit(): void {
//     this.component = window.sessionStorage.getItem('repoName');
    this.component = "GitRepositoryAnalysisSystem";
    this.getSonarqubeInfo();
  }

  getSonarqubeInfo() {

      const repoInfo = {
        component: undefined,
      };

      repoInfo.component = this.component;

      const data = JSON.stringify(repoInfo);

      const IssueRepoInfo = {
              componentKeys: undefined,
      };

      IssueRepoInfo.componentKeys = this.component;

      const issueData = JSON.stringify(IssueRepoInfo);

      this.sonarQubeService.getSonarQubeCodeSmellsService(data).subscribe(
        request => {
          this.datas = request;
          this.codeSmellsCount = this.datas.value;
        }
      );

      this.sonarQubeService.getSonarQubeVulnerabilityService(data).subscribe(
        request => {
          this.datas = request;
          this.VulnerabilityCount = this.datas.value;
        }
      );

      this.sonarQubeService.getSonarQubeBugService(data).subscribe(
        request => {
          this.datas = request;
          this.bugCount = this.datas.value;
        }
      );

      this.sonarQubeService.getSonarQubeIssueInfo(IssueRepoInfo).subscribe(
        request => {
          this.issueDatas = request;
          for (const temp_vulnerabilities of this.issueDatas.vulnerabilities){
              this.vulnerabilityInfo.push(temp_vulnerabilities.message)
          }

          for (const temp_bugs of this.issueDatas.bugs){
              this.bugsInfo.push(temp_bugs.message)
          }

          for (const temp_code_smells of this.issueDatas.code_smells){
              this.codeSmellInfo.push(temp_code_smells.message)
          }
        }
      );
  }

  goToSonarHistory(){
    this.router.navigate(['sonar-qube-history']);
  }

  goToSonarOverview(){
      this.router.navigate(['sonar-qube']);
  }

//   showBugsInfo(){
//     window.sessionStorage.getItem();
//   }
}
