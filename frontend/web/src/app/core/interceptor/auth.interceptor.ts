import {Inject, Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {EndPoint, REST_URL} from '../../config';
import {AuthService} from '../service/auth.service';
import 'rxjs/add/operator/do';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private urlNeedAuth: string[] = [];
  private urlWithoutAuth: string[] = [];
  private loginEndPoint: EndPoint;

  //todo: verify request type
  constructor(@Inject(REST_URL) urlMap: { [key: string]: EndPoint }, private authService: AuthService) {
    for (const key in urlMap) {
      if (urlMap.hasOwnProperty(key)) {
        if (key === 'Login') {
          // if is the Login url
          this.loginEndPoint = urlMap[key];
        }
        if (urlMap[key].needAuth) {
          this.urlNeedAuth.push(urlMap[key].url);
        } else {
          this.urlWithoutAuth.push(urlMap[key].url);
        }
      }
    }
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url === this.loginEndPoint.url) {
      // save the JWT
      return next.handle(req).do((httpEvent: HttpEvent<any>) => {
        if (httpEvent instanceof HttpResponse && httpEvent.headers.has('X-AUTHENTICATION')) {
          this.authService.userToken = httpEvent.headers.get('X-AUTHENTICATION');
        }
      });
    }
    if (this.urlNeedAuth.indexOf(req.url) !== -1) {
      const token = this.authService.userToken;
      if (token) {
        const secureReq = req.clone({headers: req.headers.append('X-AUTHENTICATION', token)});
        return next.handle(secureReq);
      } else {
        return next.handle(req);
      }
    }
    return next.handle(req);
  }

}
