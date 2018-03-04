import {Injectable} from '@angular/core';
import {Book} from '../model/book';
import {EndPointService} from './end-point.service';

@Injectable()
export class BookSearchService {

  constructor(private endPointService: EndPointService) {
  }

  search(searchTerm: string): Book {
    const url: string = this.endPointService.getUrl("searchPublication");
    //todo: query
    return new Book();
  }

}
