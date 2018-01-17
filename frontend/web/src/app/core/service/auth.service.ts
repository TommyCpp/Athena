import {Injectable} from '@angular/core';
import {User} from '../model/user';
import {EndPointService} from './end-point.service';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class AuthService {
  private _currentUser: User;
  private _userToken: string;

  constructor(private endPointService: EndPointService, private http: HttpClient) {
    const token = localStorage.getItem('userToken');
    console.log(token);//fixme: when localstorage has token, app-nav will have Maximum call stack size exceeded
    // fixme: looks like the token has been use millions times
    // fixme: looks like something to do with Injector
    // if (token) {
    //   //if have token, set current identity
    //   this._userToken = token;
    //   this.http.get<User>(this.endPointService.getEndPoint('GetUserByToken').url).subscribe((user) => {
    //     this._currentUser = user;
    //   })
    // }
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

  get user() {
    return this._currentUser;
  }
}
