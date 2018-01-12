import {Inject, Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {EndPoint, HttpMethod, REST_URL} from '../../config';
import {AuthService} from '../service/auth.service';
import 'rxjs/add/operator/do';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private endPointNeedAuth: EndPoint[] = [];
  private endPointWithoutAuth: EndPoint[] = [];
  private loginEndPoint: EndPoint;


  constructor(@Inject(REST_URL) urlMap: { [key: string]: EndPoint }, private authService: AuthService) {
    for (const key in urlMap) {
      if (urlMap.hasOwnProperty(key)) {
        if (key === 'Login') {
          // if is the Login url
          this.loginEndPoint = urlMap[key];
        }
        if (urlMap[key].needAuth) {
          this.endPointNeedAuth.push(urlMap[key]);
        } else {
          this.endPointWithoutAuth.push(urlMap[key]);
        }
      }
    }
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (AuthInterceptor.matchEndPoint(this.loginEndPoint, req)) {
      // save the JWT
      return next.handle(req).do((httpEvent: HttpEvent<any>) => {
        if (httpEvent instanceof HttpResponse && httpEvent.headers.has('X-AUTHENTICATION')) {
          this.authService.userToken = httpEvent.headers.get('X-AUTHENTICATION');
        }
      });
    }
    if (AuthInterceptor.hasEndPoint(this.endPointNeedAuth, req)) {
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

  private static hasEndPoint(endPoints: EndPoint[], req: HttpRequest<any>): boolean {
    for (let endPoint of endPoints) {
      if (AuthInterceptor.matchEndPoint(endPoint, req)) {
        return true;
      }
    }
    return false;
  }

  private static matchEndPoint(endPoint: EndPoint, req: HttpRequest<any>): boolean {
    let url = req.url;
    let method = <HttpMethod>req.method;
    return endPoint.url === url && endPoint.type.indexOf(method) !== -1;
  }

}
