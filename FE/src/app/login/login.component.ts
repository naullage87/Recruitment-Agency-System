import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AppService} from "../app.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  fName: any;
  lName: any;
  email: any;
  username: any;
  password: any;
  loginFalg = false;

  constructor(public router: Router, public appService: AppService) {
  }

  ngOnInit(): void {

  }


  login() {
    if (!this.username || !this.password) {
      return;
    }
    const obj = {
      username: this.username,
      password: this.password,
      type: 2
    }
    sessionStorage.setItem('user', JSON.stringify(obj))
    this.appService.login(obj).subscribe((res: any) => {
      if (res?.body == 2) {
        this.router.navigateByUrl('create');
      } else {
        this.router.navigateByUrl('home');
      }
    });
  }

  register() {
    if (!this.email || !this.password || !this.fName || !this.lName) {
      return;
    }
    const obj = {
      firstName: this.fName,
      lastName: this.lName,
      email: this.email,
      username: this.username,
      password: this.password,
      type: 2
    }
    this.appService.register(obj).subscribe((res: any) => {
      this.fName = null;
      this.lName = null;
      this.email = null;
      this.password = null;
      this.username = null;
      this.loginFalg = false;
      console.log(this.loginFalg)
      // window.location.reload()
      // this.router.navigateByUrl('login');

    });
    // this.router.navigateByUrl('login');

  }
}
