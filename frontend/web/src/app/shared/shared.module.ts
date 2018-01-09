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
  ],
  entryComponents: [LoginDialogComponent],
  providers: [],
  declarations: [
    NavbarComponent,
    LoginDialogComponent,
    CardComponent
  ],
  exports: [
    NavbarComponent,
    LoginDialogComponent
  ]
})
export class SharedModule {
}
