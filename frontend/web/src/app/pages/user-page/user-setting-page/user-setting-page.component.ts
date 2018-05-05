import {Component, OnInit} from '@angular/core';
import {User} from '../../../core/model/user';
import {AuthService} from '../../../core/service/auth.service';

@Component({
  selector: 'app-user-setting-page',
  templateUrl: './user-setting-page.component.html',
  styleUrls: ['./user-setting-page.component.scss']
})
export class UserSettingPageComponent implements OnInit {
  user: User;

  constructor(authService: AuthService) {
    this.user = authService.user;
  }


  ngOnInit() {
  }

}
