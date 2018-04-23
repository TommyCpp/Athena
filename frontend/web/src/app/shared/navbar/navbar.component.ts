import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef, MatMenuModule} from '@angular/material';
import {LoginDialogComponent} from '../login-dialog/login-dialog.component';
import {AuthService} from '../../core/service/auth.service';
import {LoginService} from '../../core/service/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  sections;
  sectionKeys;
  loginDialog: MatDialogRef<LoginDialogComponent>;

  constructor(private dialog: MatDialog, public authService: AuthService, private loginService: LoginService) {
    this.sections = {};
    this.sectionKeys = [];
  }

  ngOnInit() {
    this.loginService.getUserByStoredToken();
    this.sections = {
      'publication': {name: 'Publication', path: '/publication'},
      'admin': {name: 'Admin', path: '/admin'}
    };
    this.sectionKeys = Object.keys(this.sections);
  }

  openLogin() {
    this.loginDialog = this.dialog.open(LoginDialogComponent, {
      disableClose: true, // can only close by click close button
    });
  }

  logout() {
    this.authService.logout();
  }
}
