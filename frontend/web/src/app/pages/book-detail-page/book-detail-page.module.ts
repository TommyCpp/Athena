import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookDetailPageComponent } from './book-detail-page.component';
import {MatTabsModule} from '@angular/material/tabs';

@NgModule({
  imports: [
    CommonModule,
    MatTabsModule
  ],
  declarations: [BookDetailPageComponent]
})
export class BookDetailPageModule { }
