import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AppService} from "../app.service";

@Component({
  selector: 'app-cv-list',
  templateUrl: './cv-list.component.html',
  styleUrls: ['./cv-list.component.scss']
})
export class CvListComponent implements OnInit {


  data = {
    jobSectorId: null,
    schoolEducationQualification: null,
    minimumPasses: null,
    awardId: null,
    professionalExperianceJobSectorId: null,
    professionalExperianceYears: null,
    skill: null
  };
  tableData: any;

  constructor(public router: Router, public appService: AppService) {
    if (!sessionStorage.getItem('user')) {
      router.navigateByUrl('login')
    }
  }

  ngOnInit(): void {
    this.search();
  }

  search() {
    console.log(this.data)

    if (!this.data) {
      return;
    }

    this.appService.search(this.data).subscribe((res: any) => {
      if (res.body) {
        this.tableData = res.body;
      }
    });
  }

  download(id: any) {

    this.appService.downloadAttachmentById(id).subscribe((res: any) => {
      if (res.result.status === 200) {
        console.log('start download:', res);
        const url = window.URL.createObjectURL(res.data);
        const link = document.createElement('a');
        link.setAttribute('href', url);
        link.setAttribute('download', 'cv');
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    });
  }

  reset() {
    this.data.jobSectorId = null;
    this.data.schoolEducationQualification = null;
    this.data.minimumPasses = null;
    this.data.awardId = null;
    this.data.professionalExperianceJobSectorId = null;
    this.data.professionalExperianceYears = null;
    this.data.skill = null;

    this.search();
  }

  logout() {
    sessionStorage.removeItem('user')
    this.router.navigateByUrl('login');
  }
}
