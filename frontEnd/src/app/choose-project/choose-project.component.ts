import { Component, OnInit } from '@angular/core';
import {Router,ActivatedRoute} from '@angular/router';
import {GetProjectInfoService} from './get-project-info.service';
import {DeleteProjectService} from './delete-project.service';

@Component({
  selector: 'app-choose-project',
  templateUrl: './choose-project.component.html',
  styleUrls: ['./choose-project.component.css']
})
export class ChooseProjectComponent implements OnInit {
  projectNames = new Array();
  projectIntroduction = new Array();
  projectRepoNumbers = new Array();
  ProjectStartTime = new Array();
  datas: any;
  responsedata: any;
  item:any;
  totalProject:any;
  UserID = '';
  UserName:any;
  ChosenProjectID = '';

  constructor(private router: Router, private getProjectInfoService: GetProjectInfoService, private delProjectService: DeleteProjectService, private activerouter:ActivatedRoute ) {}

  ngOnInit(): void {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth'
    });
    this.UserName = window.sessionStorage.getItem('Username');
    this.UserID = window.sessionStorage.getItem('UserID');
    this.getTotalProjectInfo();
  }

   getTotalProjectInfo() {

      const UserProjectData = {
        userId:undefined,
      };
      UserProjectData.userId  = this.UserID;
      const data = JSON.stringify(UserProjectData);
      this.getProjectInfoService.getUserProjectData(data).subscribe(
        request => {
          this.datas = request;
          console.log(this.datas);
        }
      );
  }

  choose_repo(event) {
    console.log(event);
    const chosenId: string = event.target.id.toString();
    const chosenName: string = event.target.name.toString();
    sessionStorage.setItem('ChosenProjectID', chosenId);
    sessionStorage.setItem('ChosenProjectName', chosenName);
    console.log("chosenid:",chosenId);
    console.log("chosenid:",chosenName);
    this.router.navigate(['choose-repository']);
  }


  delete_repo(delId) {
    const deletedprojectId: string = delId.toString();
    console.log("choose to delete id:",deletedprojectId);
    if(confirm("Do You Want To Delete This Project?"))
    {
      const DeleteProject = {
            userId:undefined,
            projectId:undefined
          };
      DeleteProject.userId  = this.UserID;
      DeleteProject.projectId = deletedprojectId;
      const deldata = JSON.stringify(DeleteProject);
      this.delProjectService.deleteChosenProject(DeleteProject).subscribe(
        request => {
        this.responsedata = request;
        console.log(this.responsedata);
          if(this.responsedata.isSuccess == "true"){
            alert("Project Deleted.")
            //this.router.navigate(['choose-project']);
          }
          else{
            alert("Delete Fail.")
          }

        }
      );
    }else{
      //this.router.navigate(['choose-project']);

    }
    window.location.reload();

  }

  // tslint:disable-next-line:typedef
  goToAddProjectPage() {
    this.router.navigate(['createproject']);
  }



}
