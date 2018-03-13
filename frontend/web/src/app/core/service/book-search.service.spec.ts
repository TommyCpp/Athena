import {TestBed, inject} from '@angular/core/testing';

import {BookSearchService} from './book-search.service';
import {EndPointService} from './end-point.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {BASE_URL, REST_URL} from '../../config';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import 'rxjs/add/observable/of';
import {Observable} from 'rxjs/Observable';

describe('BookSearchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        HttpClient,
        EndPointService,
        {
          provide: BookSearchService,
          useFactory: (httpClient, endPointService) => {
            let bookSearchService = new BookSearchService(endPointService, httpClient);
            return bookSearchService;
          },
          deps: [HttpClient, EndPointService]
        },
        {
          provide: REST_URL,
          useFactory: () => {
            return {
              'SearchBook': {
                relativeUrl: '/book',
                needAuth: false,
                type: ['GET', 'POST']
              }
            };
          },

        },
        {provide: BASE_URL, useValue: 'http://localhost'}
      ],
      imports: [
        HttpClientTestingModule,
        HttpClientModule
      ]
    });
  });

  it('should be created', inject([BookSearchService], (service: BookSearchService) => {
    expect(service).toBeTruthy();
  }));


  it('should send request to query publication', inject([BookSearchService, HttpTestingController], (service: BookSearchService, httpTestController: HttpTestingController) => {
    service.search("Test");
    httpTestController.expectOne("http://localhost/book?title=Test");
  }));
});
