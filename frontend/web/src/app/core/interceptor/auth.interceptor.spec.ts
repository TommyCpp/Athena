import {async, fakeAsync, inject, TestBed, tick} from '@angular/core/testing';

import {AuthInterceptor} from './auth.interceptor';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {BASE_URL, BASE_URL_VALUE, REST_URL, REST_URL_VALUE} from '../../config';
import {AuthService} from '../service/auth.service';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule, HttpResponse} from '@angular/common/http';


describe('AuthInterceptor', () => {
  let getUserTokenStub;
  let setUserTokenStub;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        HttpClient,
        AuthInterceptor,
        {
          provide: HTTP_INTERCEPTORS,
          useClass: AuthInterceptor,
          multi: true
        },
        {
          provide: REST_URL,
          useFactory: (base_url) => {
            return {
              'Login': {
                url: base_url + '/login',
                needAuth: false,
                type: ['POST', 'GET']
              },
              'AuthMove': {
                url: base_url + '/authMove',
                needAuth: true,
                type: ['POST']
              }
            };
          },
          deps: [BASE_URL]
        },
        {provide: BASE_URL, useValue: 'http://localhost'},
        {
          provide: AuthService,
          useFactory: () => {
            let service = new AuthService();
            getUserTokenStub = spyOnProperty(service, 'userToken', 'get');
            setUserTokenStub = spyOnProperty(service, 'userToken', 'set');
            return service;
          }
        },
      ],
      imports: [
        HttpClientTestingModule,
        HttpClientModule
      ]
    });
  });

  it('Request URL need auth', inject([HttpClient, HttpTestingController, AuthService],
    (http: HttpClient, httpTestingController: HttpTestingController, authService: AuthService) => {
      const token = 'P09LI8YCQ9CHWH02C4C-314C12O3I74GC2O4323C4';
      getUserTokenStub.and.returnValue(token);

      http.post('http://localhost/authMove', null, {observe: 'response'}).subscribe((response: HttpResponse<any>) => {
        expect(response).toBeDefined();
      });
      const request = httpTestingController.expectOne('http://localhost/authMove');
      expect(request.request.method).toEqual('POST');
      expect(request.request.headers.has('X-AUTHENTICATION')).toBeTruthy();
      expect(request.request.headers.get('X-AUTHENTICATION')).toEqual(token);
      request.flush({data: true});

      httpTestingController.verify();
    }));

  it('login', inject([HttpClient, HttpTestingController, AuthService],
    (http: HttpClient, httpTestingController: HttpTestingController, authService: AuthService) => {
      const token = 'P09LI8YCQ9CHWH02C4C-314C12O3I74GC2O4323C4';
      getUserTokenStub.and.returnValue(token);

      http.post('http://localhost/login', null, {observe: 'response'}).subscribe((response: HttpResponse<any>) => {
        expect(response).toBeDefined();
        expect(setUserTokenStub).toHaveBeenCalledWith(token);
      });
      const request = httpTestingController.expectOne('http://localhost/login');
      expect(request.request.method).toEqual('POST');
      expect(request.request.headers.has('X-AUTHENTICATION')).toBeFalsy();
      request.flush({data: true}, {
        headers: {
          'X-AUTHENTICATION': token
        }
      });

      httpTestingController.verify();
    }));

  afterEach(inject([HttpTestingController], (mock: HttpTestingController) => {
    mock.verify();
  }))
});
