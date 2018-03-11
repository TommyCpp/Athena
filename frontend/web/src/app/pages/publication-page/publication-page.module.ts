import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PublicationPageComponent } from './publication-page.component';
import { SearchCardComponent } from './search-card/search-card.component';
import {MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule} from "@angular/material";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { SearchResultCardComponent } from './search-result-card/search-result-card.component';
import {RouterModule} from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    MatCardModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    RouterModule,
  ],
  declarations: [PublicationPageComponent, SearchCardComponent, SearchResultCardComponent]
})
export class PublicationPageModule { }
