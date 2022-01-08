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
  bugCount = -1;
  codeSmellsCount = -1;
  VulnerabilityCount = -1;
  component = "";
  bugsInfo = "";
  codeSmellInfo = "";
  vulnerabilityInfo = "";

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
          alert(this.issueDatas.vulnerabilities[0].message);
        }
      );
  }

  goToSonarHistory(){
    this.router.navigate(['sonar-qube-history']);
  }
  goToSonarOverview(){
      this.router.navigate(['sonar-qube']);
  }
}
