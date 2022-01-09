import {Component, OnInit} from '@angular/core';
import {SonarQubeHistoryService} from './sonar-qube-history.service';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-sonar-qube-history',
  templateUrl: './sonar-qube-history.component.html',
  styleUrls: ['./sonar-qube-history.component.css']
})

export class SonarQubeHistoryComponent implements OnInit {
    datas : any;
    component = "";
    bugHistory = [];
    codeSmellHistory = [];
    vulnerabilitiesHistory = [];

    constructor(private router: Router,private sonarQubeHistoryService: SonarQubeHistoryService) {

    }

    ngOnInit(): void {
  //     this.component = window.sessionStorage.getItem('repoName');
      this.component = "GitRepositoryAnalysisSystem";
      this.getSonarqubeHistoryInfo();
    }

    getSonarqubeHistoryInfo() {

        const repoInfo = {
          component: undefined,
        };

        repoInfo.component = this.component;

        const data = JSON.stringify(repoInfo);

        this.sonarQubeHistoryService.getSonarQubeHistoryService(data).subscribe(
          request => {
            this.datas = request;

            for (const tmp_history of this.datas.measures[0].history)
              this.codeSmellHistory.push([tmp_history.date,tmp_history.value])

            for (const tmp_history of this.datas.measures[1].history)
              this.bugHistory.push([tmp_history.date,tmp_history.value])

            for (const tmp_history of this.datas.measures[2].history)
              this.vulnerabilitiesHistory.push([tmp_history.date,tmp_history.value])

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
