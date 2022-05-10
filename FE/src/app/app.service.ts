import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(public httpClient: HttpClient) {
  }


  getHeadders() {
    try {

      let user = null;
      if (sessionStorage.getItem('user')) {
        user = sessionStorage.getItem('user');
      }
      if (user) {
        user = JSON.parse(user);
        const headerz = new HttpHeaders()
          .set('content-type', 'application/json')
          .set('Authorization', 'Basic ' + window.btoa(user?.username + ":" + user?.password));
        return headerz;
      }
      return new HttpHeaders();
    } catch (e) {
      return new HttpHeaders();
    }
  }

  login(obj: any) {
    let headerz = this.getHeadders();
    return this.httpClient.get('http://localhost:8080/api/authenticate', {
      observe: 'response',
      withCredentials: true,
      headers: headerz
    });
  }

  register(obj: any) {
    return this.httpClient.post('http://localhost:8080/api/registerUser', obj, {
      observe: 'response',
      withCredentials: true
    })

  }

  downloadAttachment() {
    let headerz = this.getHeadders();
    return this.httpClient
      .get('http://localhost:8080/api/downloadCV',
        {
          responseType: 'blob',
          observe: 'response',
          withCredentials: true,
          headers:headerz
        })
      .pipe(map((res: any) => {
        return {
          filename: 'filename.pdf',
          result: res,
          data: new Blob([res.body], {type: 'application/pdf'})
        };
      }));
  }

  downloadAttachmentById(id:any) {
    let headerz = this.getHeadders();
    return this.httpClient
      .get('http://localhost:8080/api/downloadUserCV',
        {
          responseType: 'blob',
          observe: 'response',
          withCredentials: true,
          params:{userId:id},
          headers:headerz
        })
      .pipe(map((res: any) => {
        return {
          filename: 'filename.pdf',
          result: res,
          data: new Blob([res.body], {type: 'application/pdf'})
        };
      }));
  }

  update(user: any) {
    let headerz = this.getHeadders();
    return this.httpClient.post('http://localhost:8080/api/updateUser', user, {
      observe: 'response',
      withCredentials: true,
      headers: headerz
    })

  }

  getUser() {
    let headerz = this.getHeadders();
    return this.httpClient.get('http://localhost:8080/api/getUserDetails', {
      observe: 'response',
      withCredentials: true,
      headers: headerz
    })

  }

  search(user: any) {
    let headerz = this.getHeadders();
    return this.httpClient.post('http://localhost:8080/api/searchCVs', user, {
      observe: 'response',
      withCredentials: true,
      headers: headerz
    })

  }
}
