import {Injectable} from '@angular/core';
import {User} from '../model/user';

@Injectable()
export class AuthService {
  private currentUser: User;

  constructor() {
  }


}
