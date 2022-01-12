import { Component, OnInit } from '@angular/core';
import {Router,ActivatedRoute} from '@angular/router';
import { VerifyGitRepoService } from './verify-git-repo.service';
import { CreateProjectService } from './create-project.service';


@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {
  UserName:any;
  projectImportMsg = '';
  InputGitRepoUrl: '';
  badImportMsg = '';
  datas: any;
  NameofProject = '';
  DesciptionOfProject= '';
  UserID = '';
  projectURLs: Array<string>;
  IDofProject:'';
  InputGitRepoUrlList = new Array();
  ProjectOverviewpageurl = "choose-project";

  constructor(private router: Router, private verifygitreposervice: VerifyGitRepoService,private createprojectservice: CreateProjectService ,private activerouter:ActivatedRoute) {

   }
  ngOnInit(): void {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth'
    });
    this.UserID = window.sessionStorage.getItem('UserID');
    this.UserName = window.sessionStorage.getItem('Username');
  }

  CheckGitRepoUrlVaild(){
    const GitRepoUrlData = {
      githubUrl:undefined
    };
    GitRepoUrlData.githubUrl  = this.InputGitRepoUrl;
    const data = JSON.stringify(GitRepoUrlData);
    this.verifygitreposervice.verifyGitUrlVaild(data).subscribe(
      request => {
        this.datas = request;
        console.log(this.datas);
        if (this.datas.isUrlVaild == "true"){
          if(this.InputGitRepoUrlList.indexOf(this.InputGitRepoUrl) !== -1){
            this.projectImportMsg = "Already Imported This URL.";
            this.InputGitRepoUrl = null;
          }
          else{
            this.projectImportMsg = this.InputGitRepoUrl;
            this.InputGitRepoUrlList.push(this.InputGitRepoUrl);
            this.InputGitRepoUrl = null;
          }
        }
        else{
          this.projectImportMsg = "Invalid url.";
          this.InputGitRepoUrl = null;
        }
      }
    );
  }

  CreatProject(){
    const CreateUserProjectData = {
      userId:undefined,
      projectName:undefined,
      projectDescription:undefined
    };
    CreateUserProjectData.userId  =  this.UserID.toString();
    CreateUserProjectData.projectName  =  this.NameofProject.toString();
    CreateUserProjectData.projectDescription = this.DesciptionOfProject.toString();
    const data = JSON.stringify(CreateUserProjectData);
    this.createprojectservice.createProject(data).subscribe(
      request => {
        this.datas = request;
        console.log(this.datas);
        if (this.datas.projectId != ""){
          this.IDofProject = this.datas.projectId;
          console.log("CreateProjectSuccess",this.IDofProject);
          for(var index in this.InputGitRepoUrlList){
            this.AppendRepo(index);
          }
          this.router.navigate([this.ProjectOverviewpageurl]); //create project ok ,navi to projectoverview

        }
      }
    );
  }

  AppendRepo(index){

    const RepoDataOfProject = {
          projectId:undefined,
          githubUrl:undefined
    };
     RepoDataOfProject.projectId  =  this.IDofProject.toString();
     RepoDataOfProject.githubUrl  =  this.InputGitRepoUrlList[index].toString();

     const repodata = JSON.stringify(RepoDataOfProject);
     this.createprojectservice.appendRepotoProject(repodata).subscribe(
       request => {
         this.datas = request;
         console.log(this.datas);
         if (this.datas.isSuccess == "true"){
           this.IDofProject = this.datas.projectId;
           console.log("appendRepotoProjectSuccess");
         }
       }
     );
  }

}
