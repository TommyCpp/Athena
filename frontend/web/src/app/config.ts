import {InjectionToken} from '@angular/core';
import {EndPoint} from './core/service/end-point.service';


export const BASE_URL = new InjectionToken('BASE_URL');
export const BASE_URL_VALUE = 'http://localhost:3000';

export const REST_URL = new InjectionToken('REST_URL');
export const REST_URL_VALUE: { [key: string]: EndPoint } = {
  'Login': {
    relativeUrl: '/login',
    needAuth: false,
    type: ['POST']
  },
  'GetUserById': {
    relativeUrl: '/users/{id}',
    needAuth: true,
    type: ['GET']
  },
  'GetUserByToken': {
    relativeUrl: '/user',
    needAuth: true,
    type: ['GET']
  },
  'SearchPublication': {
    relativeUrl: '/publication',
    needAuth: false,
    type: ['GET', 'POST']
  },
  'SearchBook': {
    relativeUrl: '/book',
    needAuth: false,
    type: ['GET', 'POST']
  },
  'Book': {
    relativeUrl: '/book/{isbn}',
    needAuth: false,
    type: ['GET', 'POST', 'PUT', 'PATCH']
  },
  'Journal': {
    relativeUrl: '/journal/{issn}/{year}/{issue}',
    needAuth: false,
    type: ['GET', 'POST', 'PUT', 'PATCH']
  },
  'Audio': {
    relativeUrl: '/audio/{isrc}',
    needAuth: false,
    type: ['GET', 'POST', 'PUT', 'PATCH']
  }

};
