import {Component, OnInit} from '@angular/core';
import {CardComponent} from '../../../shared/card/card.component';

@Component({
  selector: 'app-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.scss']
})
export class SearchCardComponent extends CardComponent implements OnInit {
  searchValue: string;

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
