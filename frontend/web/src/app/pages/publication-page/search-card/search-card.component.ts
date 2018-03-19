import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CardComponent} from '../../../shared/card/card.component';
import {BookSearchService} from '../../../core/service/book-search.service';
import {Publication} from '../../../core/model/publication';
import {Book} from '../../../core/model/book';
import {Observable} from 'rxjs/Observable';
import {Observer, PartialObserver} from 'rxjs/Observer';

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
    //todo:test
    let publicationSearchResult: Observable<Publication[]> = this.bookSearchService.search(this.searchValue);
    publicationSearchResult.subscribe(response => {
      this.searchComplete.emit(response);
    });

  }

}
