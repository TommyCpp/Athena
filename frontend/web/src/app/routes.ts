import {Routes} from '@angular/router';
import {HomePageComponent} from './pages/home-page/home-page.component';
import {PublicationPageModule} from "./pages/publication-page/publication-page.module";
import {PublicationPageComponent} from "./pages/publication-page/publication-page.component";
import {BookDetailPageComponent} from './pages/book-detail-page/book-detail-page.component';

export const ATHENA_ROUTES: Routes = [
  {path: '', component: HomePageComponent, pathMatch: 'full'},
  {path: 'publication', component: PublicationPageComponent},
  {path: 'book/:isbn', component: BookDetailPageComponent}
];
