import {Injectable} from '@angular/core';
import {EndPointService} from './end-point.service';
import {HttpClient} from '@angular/common/http';
import {Book} from '../model/book';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class BookService {

  constructor(private endPointService: EndPointService, private http: HttpClient) {
  }

  get(isbn: string): Observable<Book[]> {
    return this.http.get<Book[]>(this.endPointService.getUrl("Book", {isbn: isbn}));
  }

}
