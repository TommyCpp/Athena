import {Injectable, Injector} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {AuthService} from '../service/auth.service';
import 'rxjs/add/operator/do';
import {EndPointService} from '../service/end-point.service';
import {User} from '../model/user';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {


  constructor(private endPointService: EndPointService, private injector: Injector) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authService = this.injector.get(AuthService);
    if (this.endPointService.isRequestIsLogin(req)) {
      // add the JWT
      return next.handle(req).do((httpEvent: HttpEvent<any>) => {
        if (httpEvent instanceof HttpResponse && httpEvent.headers.has('X-AUTHENTICATION')) {
          authService.userToken = httpEvent.headers.get('X-AUTHENTICATION');
          authService.user = httpEvent.body as User;
        }
      });
    }
    if (this.endPointService.isRequestNeedAuth(req)) {
      const token = authService.userToken;
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
