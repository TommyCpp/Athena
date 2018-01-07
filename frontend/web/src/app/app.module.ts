import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {NavbarComponent} from './shared/navbar/navbar.component';
import {
  MatButtonModule, MatDialogModule, MatFormFieldModule, MatIconModule, MatInputModule, MatMenuModule,
  MatToolbarModule
} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Router, RouterModule} from '@angular/router';
import {ATHENA_ROUTES} from './routes';
import {HomePageComponent} from './pages/home-page/home-page.component';
import {LoginDialogComponent} from './shared/login-dialog/login-dialog.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LoginService} from './service/login.service';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomePageComponent,
    LoginDialogComponent
  ],
  imports: [
    BrowserModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatDialogModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    RouterModule.forRoot(ATHENA_ROUTES),
  ],
  providers: [LoginService],
  entryComponents: [LoginDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
