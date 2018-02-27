import { TestBed, inject } from '@angular/core/testing';

import { BookSearchService } from './book-search.service';

describe('BookSearchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BookSearchService]
    });
  });

  it('should be created', inject([BookSearchService], (service: BookSearchService) => {
    expect(service).toBeTruthy();
  }));
});
