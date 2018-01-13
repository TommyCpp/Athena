import {TestBed, inject} from '@angular/core/testing';

import {EndPoint, EndPointService} from './end-point.service';
import {HttpRequest} from '@angular/common/http';

describe('EndPointService', () => {
  let endPointService: EndPointService;
  let base_url = 'http://localhost:6006';
  let endPointMap: { [key: string]: EndPoint } = {
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
  beforeEach(() => {
    endPointService = new EndPointService(endPointMap);
  });

  it('should be created', () => {
    const req1 = new HttpRequest('POST', base_url + '/authMove', null);
    expect(endPointService.isRequestNeedAuth(req1)).toBeTruthy();
    const req2 = new HttpRequest('POST', base_url + "/login", null);
    expect(endPointService.isRequestIsLogin(req2)).toBeTruthy();
    const req3 = new HttpRequest('GET', base_url + '/authMove');
    expect(endPointService.isRequestNeedAuth(req3)).toBeFalsy();
  });
});