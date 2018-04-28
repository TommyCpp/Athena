import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatTabsModule} from '@angular/material/tabs';
import {MatGridListModule} from '@angular/material/grid-list';
import {SharedModule} from '../../shared/shared.module';
import {MatDividerModule} from '@angular/material/divider';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material';
import {MatExpansionModule} from '@angular/material/expansion';
import {UserPageComponent} from './user-page.component';
import {UserSettingPageComponent} from './user-setting-page/user-setting-page.component';
import {AuthService} from '../../core/service/auth.service';
import {RouterModule} from '@angular/router';
import {ATHENA_ROUTES} from '../../routes';

@NgModule({
  imports: [
    CommonModule,
    MatTabsModule,
    MatGridListModule,
    SharedModule,
    MatDividerModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
    MatExpansionModule,
    RouterModule.forRoot(ATHENA_ROUTES)
  ],
  providers: [
    AuthService
  ],
  declarations: [UserPageComponent, UserSettingPageComponent]
})
export class UserPageModule {
}
