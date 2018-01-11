import {InjectionToken} from '@angular/core';

export const BASE_URL = new InjectionToken('BASE_URL');
export const BASE_URL_VALUE = 'localhost:6006';

export const REST_URL = new InjectionToken('REST_URL');
export const REST_URL_VALUE = {
  'Login': {
    url: '/login',
    needAuth: false,
    type: ['POST']
  }
};
