import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-analysis',
  templateUrl: './analysis.component.html',
  styleUrls: ['./analysis.component.css']

})
export class AnalysisComponent implements OnInit {
  owner: any;
  repoName: any;
  RepoMemberCounts = 50;

  constructor(private router: Router, private activerouter: ActivatedRoute) {

  }

  ngOnInit(): void {
      this.repoName = window.sessionStorage.getItem('repoName');
      this.owner = window.sessionStorage.getItem('owner');

  }

  // tslint:disable-next-line:typedef
  goToCommitTrendPage() {
    // this.router.navigateByUrl('commit-trend');
    this.router.navigate(['commit-trend']);
  }

  // tslint:disable-next-line:typedef
  goToCodeBasePage() {
    // this.router.navigateByUrl('code-base');
    this.router.navigate(['code-base']);
  }

  // tslint:disable-next-line:typedef
  goToIssueTrackPage() {
    // this.router.navigateByUrl('issue-track');
    this.router.navigate(['issue-track']);
  }

  goToSonarQubePage() {
      // this.router.navigateByUrl('issue-track');
      this.router.navigate(['sonar-qube']);
    }
}
