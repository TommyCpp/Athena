import {TestBed, inject} from '@angular/core/testing';

import {BookService} from './book.service';
import {EndPointService} from './end-point.service';
import {HttpClient} from '@angular/common/http';

describe('BookService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        BookService,
        EndPointService,
        HttpClient
      ]
    });
  });

  it('should be created', inject([BookService], (service: BookService) => {
    expect(service).toBeTruthy();
  }));
});
