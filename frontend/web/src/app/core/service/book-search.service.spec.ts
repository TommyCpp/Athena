import {TestBed, inject} from '@angular/core/testing';

import {BookSearchService} from './book-search.service';
import {EndPointService} from './end-point.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {BASE_URL, REST_URL} from '../../config';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

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
          useFactory: (base_url) => {
            return {
              'SearchPublication': {
                url: '/publication',
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
    service.search("Test").subscribe((data: any) => {

    });
    httpTestController.expectOne("http://localhost/publication?title=Test");
  }))
});
