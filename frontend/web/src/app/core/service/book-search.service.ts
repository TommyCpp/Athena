import {Injectable} from '@angular/core';
import {Book} from '../model/book';
import {EndPointService} from './end-point.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class BookSearchService {

  constructor(private endPointService: EndPointService, private http: HttpClient) {
  }

  search(searchTerm: string): Observable<Book[]> {
    const url: string = this.endPointService.getUrl("SearchBook");
    return this.http.get<Book[]>(url + `?title=${searchTerm}`);
  }

}
