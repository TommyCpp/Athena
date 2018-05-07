import {getTestBed, inject, TestBed} from '@angular/core/testing';

import {UserService} from './user.service';
import {EndPointService} from './end-point.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {BASE_URL, REST_URL} from '../../config';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {HttpClientTestingBackend} from '@angular/common/http/testing/src/backend';

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        UserService,
        EndPointService,
        HttpClient,
        {
          provide: REST_URL,
          useValue: {
            'GetBorrowByUser': {
              relativeUrl: '/user/borrow',
              needAuth: true,
              type: ['GET']
            }
          }
        },
        {provide: BASE_URL, useValue: 'http://localhost'},
      ],
      imports: [
        HttpClientTestingModule,
      ]
    });
    this.service = TestBed.get(UserService);
    this.httpTestingController = TestBed.get(HttpTestingController);
  });


  it('should be created', inject([UserService], (service: UserService) => {
    expect(service).toBeTruthy();
  }));

  it('should send user borrow query request', inject([UserService, HttpTestingController], (userService: UserService, httpTestingController: HttpTestingController) => {
    let borrows = userService.borrow();
    borrows.subscribe((data: any) => {

    });
    httpTestingController.expectOne("http://localhost/user/borrow");
  }));
});
