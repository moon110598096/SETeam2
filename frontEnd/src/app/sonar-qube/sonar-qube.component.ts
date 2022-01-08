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
  bugsInfo = "";
  codeSmellInfo = "";
  vulnerabilityInfo = "";

  constructor(private sonarQubeService: SonarQubeService) {
  }

  ngOnInit(): void {
//     this.component = window.sessionStorage.getItem('repoName');
    this.component = "GitRepositoryAnalysisSystem";
    alert(this.component);
    this.getSonarqubeInfo();
    this.createInfo();
  }

  createInfo(){
    this.bugsInfo += "Use try-with-resources or close this \"PreparedStatement\" in a \"finally\" clause";

    this.codeSmellInfo += "Use a logger to log this exception. \n";
    this.codeSmellInfo += "Add a \"try/catch\" block for \"getWriter\".\n";
    this.codeSmellInfo += "Add a \"try/catch\" block for \"getCodeSmellInfoJsonArray\".\n";
    this.codeSmellInfo += "Add a \"try/catch\" block for \"getString\".\n";

    this.vulnerabilityInfo += "Remove this unused import 'java.util.Objects'.\n";
    this.vulnerabilityInfo += "Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.\n";
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
