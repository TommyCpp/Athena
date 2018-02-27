import {Injectable} from '@angular/core';
import {Book} from '../model/book';

@Injectable()
export class BookSearchService {

  constructor() {
  }

  search(searchTerm: string): Book {
    //todo:finish function
    return new Book();
  }

}
