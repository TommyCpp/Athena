import {TestBed, inject} from '@angular/core/testing';

import {AuthInterceptor} from './auth.interceptor';

describe('AuthInterceptor', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthInterceptor]
    });
  });

  it('should be created', inject([AuthInterceptor], (interceptor: AuthInterceptor) => {
    expect(interceptor).toBeTruthy();
  }));
});
