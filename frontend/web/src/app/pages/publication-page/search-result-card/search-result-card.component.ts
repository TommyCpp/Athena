import {Component, OnInit} from '@angular/core';
import {Publication} from '../../../core/model/publication';

@Component({
  selector: 'app-search-result-card',
  templateUrl: './search-result-card.component.html',
  styleUrls: ['./search-result-card.component.scss']
})
export class SearchResultCardComponent implements OnInit {

  constructor(private publication: Publication) {
  }

  ngOnInit() {
  }

}
