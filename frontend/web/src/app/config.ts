import {InjectionToken} from '@angular/core';
import {EndPoint} from './core/service/end-point.service';


export const BASE_URL = new InjectionToken('BASE_URL');
export const BASE_URL_VALUE = 'http://localhost:3000';

export const REST_URL = new InjectionToken('REST_URL');
export const REST_URL_VALUE: { [key: string]: EndPoint } = {
  'Login': {
    url: '/login',
    needAuth: false,
    type: ['POST']
  },
  'GetUserById': {
    url: '/users/{id}',
    needAuth: true,
    type: ['GET']
  },
  'GetUserByToken': {
    url: '/user',
    needAuth: true,
    type: ['GET']
  },
  'SearchPublication': {
    url: '/publication',
    needAuth: false,
    type: ['GET', 'POST']
  },
  'SearchBook': {
    url: '/book',
    needAuth: false,
    type: ['GET', 'POST']

  }

};
