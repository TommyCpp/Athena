import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {LoginService} from '../../core/service/login.service';
import {HttpResponse} from '@angular/common/http';
import {AuthService} from '../../core/service/auth.service';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss']
})
export class LoginDialogComponent implements OnInit {
  ifHidePassword: Boolean = true;

  constructor(public dialogRef: MatDialogRef<LoginDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private loginService: LoginService, private authService: AuthService) {
  }

  ngOnInit() {
  }

  onSubmit(formValue: object): void {
    this.loginService.login(formValue['id'], formValue['password'])
      .subscribe({
        next: (response: HttpResponse<any>) => {
          this.dialogRef.close();
        },
      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
