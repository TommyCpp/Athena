import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PublicationPageComponent } from './publication-page.component';
import { SearchCardComponent } from './search-card/search-card.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [PublicationPageComponent, SearchCardComponent]
})
export class PublicationPageModule { }
