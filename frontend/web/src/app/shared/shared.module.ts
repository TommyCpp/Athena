import {NgModule} from '@angular/core';
import {
  MatButtonModule,
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
    RouterModule.forRoot(ATHENA_ROUTES)
  ],
  entryComponents: [LoginDialogComponent],
  providers: [],
  declarations: [
    NavbarComponent,
    LoginDialogComponent,
    CardComponent,
    PublicationPrefaceComponent
  ],
  exports: [
    NavbarComponent,
    LoginDialogComponent,
    PublicationPrefaceComponent
  ]
})
export class SharedModule {
}
