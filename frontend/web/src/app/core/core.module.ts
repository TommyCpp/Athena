import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginService} from './service/login.service';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {AuthInterceptor} from './interceptor/auth.interceptor';
import {AuthService} from './service/auth.service';
import {EndPointService} from './service/end-point.service';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    HttpClient,
    LoginService,
    AuthService,
    EndPointService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  declarations: []
})
export class CoreModule {
}
