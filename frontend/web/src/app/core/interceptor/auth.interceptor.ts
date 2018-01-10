import {Inject, Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {REST_URL} from '../../config';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {


  constructor(@Inject(REST_URL) private urlMap) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url) {
      //  todo:implement
    }
    return null;
  }

}
