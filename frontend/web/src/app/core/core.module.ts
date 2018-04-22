import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginService} from './service/login.service';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {AuthInterceptor} from './interceptor/auth.interceptor';
import {AuthService} from './service/auth.service';
import {EndPointService} from './service/end-point.service';
import {UserRolePipe} from './pipe/user-role.pipe';
import {BookSearchService} from './service/book-search.service';
import {BookService} from './service/book.service';
import {CopyStatusPipe} from './pipe/copy-status.pipe';

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
    BookSearchService,
    BookService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  declarations: [UserRolePipe, CopyStatusPipe],
  exports: [UserRolePipe, CopyStatusPipe]
})
export class CoreModule {
}
