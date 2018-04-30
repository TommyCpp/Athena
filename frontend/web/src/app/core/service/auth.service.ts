import {Injectable} from '@angular/core';
import {User} from '../model/user';


@Injectable()
export class AuthService {
  private _currentUser: User;
  private _userToken: string;

  constructor() {
  }

  public logout() {
    this._currentUser = null;
    localStorage.removeItem('userToken');
    this._userToken = null;
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

  /**
   * Get user.
   * <b>Please note that this cannot be used as means to determine whether is loggedin</b>
   * * */
  get user() {
    return this._currentUser;
  }

  get isLoggedIn(){
    return this.userToken != null;
  }
}
