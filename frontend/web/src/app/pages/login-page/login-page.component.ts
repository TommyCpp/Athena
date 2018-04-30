import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {LoginDialogComponent} from '../../shared/login-dialog/login-dialog.component';
import {ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit,OnDestroy {
  loginDialog: MatDialogRef<LoginDialogComponent>;

  constructor(private dialog: MatDialog, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.queryParams.subscribe((param: Params) => {
      if(param["redirectTo"]){
        this.loginDialog = this.dialog.open(LoginDialogComponent, {
          disableClose: true,
          hasBackdrop: false,
          data: {
            "isLoginPage": true,
            "redirectTo": param["redirectTo"]
          }
        });
      }
      else{
        this.loginDialog = this.dialog.open(LoginDialogComponent, {
          disableClose: true,
          hasBackdrop: false,
          data: {
            "isLoginPage": true
          }
        });
      }
    });

  }

  ngOnInit() {

  }

  ngOnDestroy(): void {
    this.loginDialog.close();
  }


}
