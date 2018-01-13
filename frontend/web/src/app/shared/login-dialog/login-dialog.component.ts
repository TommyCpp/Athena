import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {LoginService} from '../../core/service/login.service';
import {HttpResponse} from '@angular/common/http';

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
      .subscribe((response:HttpResponse<any>) => {

      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
