import {InjectionToken} from '@angular/core';

export type EndPoint = {
  url: string;
  needAuth: boolean;
  type: ('POST'|'GET'|'OPTION'|'PUT'|'PATCH')[];
}

export const BASE_URL = new InjectionToken('BASE_URL');
export const BASE_URL_VALUE = 'localhost:6006';

export const REST_URL = new InjectionToken('REST_URL');
export const REST_URL_VALUE: { [key: string]: EndPoint } = {
  'Login': {
    url: '/login',
    needAuth: false,
    type: ['POST']
  }
};
