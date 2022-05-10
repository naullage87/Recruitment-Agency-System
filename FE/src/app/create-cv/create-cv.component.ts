import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AppService} from "../app.service";

export class Resume {
  schoolEducations: SchoolEducations[] = [];
  higherEducations: HigherEducations[] = [];
  professionalExperiances: ProfessionalExperiances[] = [];
  skillsAndAchievments: SkillsAndAchievments[] = [];
  jobSectorId: any;
  gender: any;
  maried: any;
  nationality: any;
  nic: any;
  address1: any;
  address2: any;
  address3: any;
  telephone: any;
  mobile: any;
  email: any;
  firstName: any;
  lastName: any;
  dateOfBirth: any;
}

export class SchoolEducations {
  qualification: any;
  scheme: any;
  dateOfAchived: any;
  numberOfPasses: any;
}

export class HigherEducations {
  awardId: any;
  affiliatedInstitute: any;
  areaOfStudy: any;
  description: any;
  result: any;
}

export class ProfessionalExperiances {
  jobSectorId: any;
  organization: any;
  designation: any;
  fromDate: any;
  toDate: any;
  description: any;
}

export class SkillsAndAchievments {
  description: any;
}

@Component({
  selector: 'app-create-cv',
  templateUrl: './create-cv.component.html',
  styleUrls: ['./create-cv.component.scss']
})
export class CreateCvComponent implements OnInit {

  public resume: Resume = new Resume();
  updated = false;

  constructor(public router: Router, public appService: AppService) {
    if (!sessionStorage.getItem('user')) {
      router.navigateByUrl('login')
    }
  }

  ngOnInit(): void {
    this.getUser();
  }

  addSchool() {
    const schoolEducations = new SchoolEducations()
    this.resume.schoolEducations.push(schoolEducations)
  }

  addHSchool() {
    const higherEducations = new HigherEducations()
    this.resume.higherEducations.push(higherEducations)
  }

  addExp() {
    const professionalExperiances = new ProfessionalExperiances()
    this.resume.professionalExperiances.push(professionalExperiances)
  }

  addSkill() {
    const skillsAndAchievments = new SkillsAndAchievments()
    this.resume.skillsAndAchievments.push(skillsAndAchievments)
  }

  getUser(){
    this.appService.getUser().subscribe((res: any) => {
      if(res.body){
        this.resume = res.body;
      }

      // this.router.navigateByUrl('login');

    });
  }

  createCv() {
    console.log(this.resume)

    if (!this.resume) {
      return;
    }

    this.appService.update(this.resume).subscribe((res: any) => {
     this.updated = true;

     setTimeout(()=>{
       this.updated = false;
     },3000)
      // this.router.navigateByUrl('login');

    });
  }

  logout() {
    sessionStorage.removeItem('user')
    this.router.navigateByUrl('login');
  }

  download() {
    this.appService.downloadAttachment().subscribe((res: any) => {
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
}
