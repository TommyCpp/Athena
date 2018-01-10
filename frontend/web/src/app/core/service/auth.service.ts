import {Injectable} from '@angular/core';
import {User} from '../model/user';


@Injectable()
export class AuthService {
  private _currentUser: User;
  private _userToken: string;

  constructor() {
  }

  get userToken() {
    return this._userToken ? this._userToken : localStorage.getItem('userToken');
  }

  set userToken(token: string) {
    this._userToken = token;
    localStorage.setItem('userToken', token);
  }


}
