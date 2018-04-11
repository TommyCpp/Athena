import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BookDetailPageComponent} from './book-detail-page.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatGridListModule} from '@angular/material/grid-list';
import {SharedModule} from '../../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    MatTabsModule,
    MatGridListModule,
    SharedModule
  ],
  declarations: [BookDetailPageComponent]
})
export class BookDetailPageModule {
}
