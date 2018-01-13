import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {EndPoint, EndPointService} from './end-point.service';

@Injectable()
export class LoginService {

  constructor(private http: HttpClient, private endPointService: EndPointService) {
  }

  public login(username: string, password: string): Observable<HttpResponse<any>> {
    let loginEndPoint: EndPoint = this.endPointService.getEndPoint('Login');
    return this.http.post(loginEndPoint.url, {
      username: username,
      password: password,
    }, {observe: 'response'});
  }

}
