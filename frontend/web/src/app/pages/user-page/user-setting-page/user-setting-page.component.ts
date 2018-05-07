import {Component, OnInit} from '@angular/core';
import {User} from '../../../core/model/user';
import {AuthService} from '../../../core/service/auth.service';
import {FormControl, Validator, Validators} from '@angular/forms';
import {Router, RouterStateSnapshot} from '@angular/router';
import {USER_SETTING} from '../../../config';

@Component({
  selector: 'app-user-setting-page',
  templateUrl: './user-setting-page.component.html',
  styleUrls: ['./user-setting-page.component.scss']
})
export class UserSettingPageComponent implements OnInit {
  user: User;
  currentUrl: string;

  PASS_MAX_LENGTH = USER_SETTING.PASS_MAX_LENGTH;

  username: FormControl;
  wechatId: FormControl;
  phoneNumber: FormControl;
  email: FormControl;
  oldPass: FormControl;
  newPass: FormControl;
  newPassRepeat: FormControl;

  constructor(authService: AuthService, router: Router) {
    this.user = authService.user;
    this.username = new FormControl(this.user.username, [Validators.required]);
    this.wechatId = new FormControl(this.user.wechatId, [Validators.required]);
    this.phoneNumber = new FormControl(this.user.phoneNumber, [Validators.required]);
    this.email = new FormControl(this.user.email, [Validators.required, Validators.email]);
    this.oldPass = new FormControl('', [Validators.required, Validators.maxLength(this.PASS_MAX_LENGTH)]);
    this.newPass = new FormControl('', [Validators.required, Validators.maxLength(this.PASS_MAX_LENGTH)]);
    this.newPassRepeat = new FormControl('', [Validators.required, Validators.maxLength(this.PASS_MAX_LENGTH)]);
    this.currentUrl = router.url;
  }


  ngOnInit() {
  }

}
