import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {ATHENA_ROUTES} from './routes';
import {CoreModule} from './core/core.module';
import {HomePageModule} from './pages/home-page/home-page.module';
import {SharedModule} from './shared/shared.module';
import {BrowserModule} from '@angular/platform-browser';


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    RouterModule.forRoot(ATHENA_ROUTES),
    BrowserModule,
    CoreModule,
    SharedModule,
    HomePageModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
