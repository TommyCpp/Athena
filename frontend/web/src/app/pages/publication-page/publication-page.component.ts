import {Component, OnInit} from '@angular/core';
import {Publication} from '../../core/model/publication';

@Component({
  selector: 'app-publication-page',
  templateUrl: './publication-page.component.html',
  styleUrls: ['./publication-page.component.scss']
})
export class PublicationPageComponent implements OnInit {
  private searchResult: Publication[] = [];

  constructor() {
  }

  ngOnInit() {
  }

  handleSearchComplete($event) {
    this.searchResult = $event;
  }
}
