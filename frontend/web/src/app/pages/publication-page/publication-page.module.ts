import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PublicationPageComponent } from './publication-page.component';
import { SearchCardComponent } from './search-card/search-card.component';
import {MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule} from "@angular/material";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

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
  ],
  declarations: [PublicationPageComponent, SearchCardComponent]
})
export class PublicationPageModule { }
