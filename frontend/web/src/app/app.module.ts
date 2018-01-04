import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {NavbarComponent} from './shared/navbar/navbar.component';
import {MatButtonModule, MatMenuModule, MatToolbarModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Router, RouterModule} from '@angular/router';
import {ATHENA_ROUTES} from './routes';
import { HomePageComponent } from './pages/home-page/home-page.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomePageComponent
  ],
  imports: [
    BrowserModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(ATHENA_ROUTES),
    Router
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
