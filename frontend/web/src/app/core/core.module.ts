import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginService} from './service/login.service';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthInterceptor} from './interceptor/auth.interceptor';
import {AuthService} from './service/auth.service';

@NgModule({
  imports: [
    CommonModule
  ],
  providers: [
    LoginService,
    AuthService,
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
