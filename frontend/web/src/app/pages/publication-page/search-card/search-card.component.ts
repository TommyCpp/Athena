import {Component, Input, OnInit} from '@angular/core';
import {CardComponent} from '../../../shared/card/card.component';
import {BookSearchService} from '../../../core/service/book-search.service';

@Component({
  selector: 'app-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.scss']
})
export class SearchCardComponent extends CardComponent implements OnInit {
  @Input()
  searchValue: string;

  constructor(private bookSearchService: BookSearchService) {
    super();
  }

  ngOnInit() {
  }

  search() {

  }

}
