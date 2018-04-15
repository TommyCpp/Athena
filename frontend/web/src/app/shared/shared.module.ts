import {NgModule} from '@angular/core';
import {
  MatButtonModule, MatCardModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatToolbarModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavbarComponent} from './navbar/navbar.component';
import {LoginDialogComponent} from './login-dialog/login-dialog.component';
import {CardComponent} from './card/card.component';
import {RouterModule} from "@angular/router";
import {ATHENA_ROUTES} from "../routes";
import {PublicationPrefaceComponent} from './publication-preface/publication-preface.component';
import {PublicationBasicInfoComponent} from './publication-basic-info/publication-basic-info.component';
import {MatListModule} from '@angular/material/list';
import {MatChipsModule} from '@angular/material/chips';

@NgModule({
  imports: [
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
    MatListModule,
    MatCardModule,
    MatChipsModule
  ],
  entryComponents: [],
  providers: [],
  declarations: [
    NavbarComponent,
    LoginDialogComponent,
    CardComponent,
    PublicationPrefaceComponent,
    PublicationBasicInfoComponent
  ],
  exports: [
    NavbarComponent,
    LoginDialogComponent,
    PublicationPrefaceComponent,
    PublicationBasicInfoComponent
  ]
})
export class SharedModule {
}
