import {Injectable} from '@angular/core';
import {EndPointService} from './end-point.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Borrow} from '../model/borrow';
import {Page} from '../model/page';

@Injectable()
export class UserService {

  constructor(private endPointService: EndPointService, private httpClient: HttpClient) {

  }

  borrow(): Observable<Borrow[] | Page<Borrow>> {
    return this.httpClient.get<Borrow[] | Page<Borrow>>(this.endPointService.getUrl("GetBorrowByUser"));
  }


}
