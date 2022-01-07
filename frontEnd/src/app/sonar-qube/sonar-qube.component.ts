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
  bugCount = -1;
  codeSmellsCount = -1;
  VulnerabilityCount = -1;
  component = "";
  constructor(private sonarQubeService: SonarQubeService) {
  }

  ngOnInit(): void {
//     this.component = window.sessionStorage.getItem('repoName');
    this.component = "HappyCamp";
    this.getSonarqubeInfo();
  }

  getSonarqubeInfo() {

      const repoInfo = {
        component: undefined,
      };

      repoInfo.component = this.component;

      const data = JSON.stringify(repoInfo);

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

  }
}
