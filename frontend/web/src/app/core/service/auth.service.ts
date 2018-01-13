import {Injectable} from '@angular/core';
import {User} from '../model/user';


@Injectable()
export class AuthService {
  private _currentUser: User;
  private _userToken: string;

  constructor() {
    let token = localStorage.getItem('userToken');
    if(token){
      //if have token
      this._userToken = token;
      //todo: query user's info
    }
  }

  get userToken() {
    return this._userToken ? this._userToken : localStorage.getItem('userToken');
  }

  set userToken(token: string) {
    this._userToken = token;
    localStorage.setItem('userToken', token);
  }


  set user(user: User) {
    this._currentUser = user;
  }

  get user(){
    return this._currentUser;
  }
}
