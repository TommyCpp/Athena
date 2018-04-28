import {Routes} from '@angular/router';
import {HomePageComponent} from './pages/home-page/home-page.component';
import {PublicationPageModule} from "./pages/publication-page/publication-page.module";
import {PublicationPageComponent} from "./pages/publication-page/publication-page.component";
import {BookDetailPageComponent} from './pages/book-detail-page/book-detail-page.component';
import {UserPageComponent} from './pages/user-page/user-page.component';
import {UserSettingPageComponent} from './pages/user-page/user-setting-page/user-setting-page.component';
import {LoggedInGuard} from './core/guard/logged-in.guard';

export const ATHENA_ROUTES: Routes = [
  {path: '', component: HomePageComponent, pathMatch: 'full'},
  {path: 'publication', component: PublicationPageComponent},
  {path: 'book/:isbn', component: BookDetailPageComponent},
  {
    path: 'user', component: UserPageComponent, canActivate: [LoggedInGuard], children: [
      {path: '', redirectTo: "setting", pathMatch: 'full'},
      {path: 'setting', component: UserSettingPageComponent}
    ]
  },
];
