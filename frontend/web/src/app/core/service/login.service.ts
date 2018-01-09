import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class LoginService {

  constructor() {
  }

  public login(username: string, password: string): Observable<any> {
    // todo: login
    return null;
  }

}
