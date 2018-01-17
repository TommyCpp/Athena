import {TestBed, inject} from '@angular/core/testing';

import {AuthService} from './auth.service';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {EndPoint, EndPointService} from "./end-point.service";
import {User} from "../model/user";

describe('AuthService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule,
      ],
      providers: [AuthService,
        HttpClient
      ]
    });
  });

  it('should be created', inject([AuthService], (service: AuthService) => {
    expect(service).toBeTruthy();
  }));

  it('retrieve user info', inject([HttpClient, HttpTestingController], (http: HttpClient, testController: HttpTestingController) => {
    let currentUser: User;
    http.get<User>('http://localhost/user').subscribe((user) => {
      currentUser = user;
      expect(currentUser.username).toEqual('test')
    });
    const request = testController.expectOne('http://localhost/user');
    const responseUser = new User(1, 'test');
    request.flush(responseUser);

  }));
});
