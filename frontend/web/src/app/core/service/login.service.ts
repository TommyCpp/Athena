import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {EndPoint, EndPointService} from './end-point.service';
import {AuthService} from './auth.service';
import {User} from '../model/user';

@Injectable()
export class LoginService {

  constructor(private http: HttpClient, private endPointService: EndPointService, private authService: AuthService) {

  }

  public getUserByStoredToken() {
    if (this.authService.userToken && !this.authService.user) {
      this.http.get<User>(this.endPointService.getEndPoint('GetUserByToken').url).subscribe((user) => {
        this.authService.user = user;
      })
    }
  }

  public login(id: string, password: string): Observable<HttpResponse<any>> {
    let loginEndPoint: EndPoint = this.endPointService.getEndPoint('Login');
    return this.http.post(loginEndPoint.url, {
      id: id,
      password: password,
    }, {observe: 'response'});
  }

}
