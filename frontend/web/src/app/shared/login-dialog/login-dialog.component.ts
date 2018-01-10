import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {LoginService} from '../../core/service/login.service';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss']
})
export class LoginDialogComponent implements OnInit {
  ifHidePassword: Boolean = true;

  constructor(public dialogRef: MatDialogRef<LoginDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, public loginService: LoginService) {
  }

  ngOnInit() {
  }

  onSubmit(formValue: object): void {
    this.loginService.login(formValue['username'], formValue['password'])
      .subscribe((value) => {
      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
