import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CardComponent} from '../../../shared/card/card.component';
import {BookSearchService} from '../../../core/service/book-search.service';
import {Publication} from '../../../core/model/publication';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.scss']
})
export class SearchCardComponent extends CardComponent implements OnInit {
  @Input()
  searchValue: string;

  @Output()
  searchComplete: EventEmitter<Publication[]> = new EventEmitter();

  constructor(private bookSearchService: BookSearchService) {
    super();
  }

  ngOnInit() {
  }

  search() {
    let publicationSearchResult: Observable<Publication[]> = this.bookSearchService.search(this.searchValue);
    publicationSearchResult.subscribe((response: Publication[]) => {
      //todo: attribute name decorator with recursion.
      this.searchComplete.emit(response);
    });

  }

}
