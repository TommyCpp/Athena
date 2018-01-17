import {Injectable} from '@angular/core';
import {User} from '../model/user';


@Injectable()
export class AuthService {
  private _currentUser: User;
  private _userToken: string;

  constructor() {
  }


  get userToken() {
    if (this._userToken) {
      return this._userToken;
    }
    else {
      this._userToken = localStorage.getItem('userToken');
      return this._userToken;
    }
  }

  set userToken(token: string) {
    this._userToken = token;
    localStorage.setItem('userToken', token);
  }


  set user(user: User) {
    this._currentUser = user;
  }

  get user() {
    return this._currentUser;
  }
}
