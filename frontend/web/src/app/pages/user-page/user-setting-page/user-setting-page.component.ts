import {Component, OnInit} from '@angular/core';
import {User} from '../../../core/model/user';
import {AuthService} from '../../../core/service/auth.service';
import {FormControl, Validator, Validators} from '@angular/forms';
import {Router, RouterStateSnapshot} from '@angular/router';

@Component({
  selector: 'app-user-setting-page',
  templateUrl: './user-setting-page.component.html',
  styleUrls: ['./user-setting-page.component.scss']
})
export class UserSettingPageComponent implements OnInit {
  user: User;
  username: FormControl;
  wechatId: FormControl;
  phoneNumber: FormControl;
  email: FormControl;
  currentUrl: string;

  constructor(authService: AuthService, router: Router) {
    this.user = authService.user;
    this.username = new FormControl(this.user.username, [Validators.required]);
    this.wechatId = new FormControl(this.user.wechatId, [Validators.required]);
    this.phoneNumber = new FormControl(this.user.phoneNumber, [Validators.required]);
    this.email = new FormControl(this.user.email, [Validators.required, Validators.email]);
    this.currentUrl = router.url;
  }


  ngOnInit() {
  }

}
