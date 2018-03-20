import {async, ComponentFixture, fakeAsync, inject, TestBed, tick} from '@angular/core/testing';

import {SearchCardComponent} from './search-card.component';
import {BookSearchService} from '../../../core/service/book-search.service';
import {EndPointService} from '../../../core/service/end-point.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {BASE_URL, REST_URL} from '../../../config';
import {Publication} from '../../../core/model/publication';
import {Observable} from 'rxjs/Observable';
import {MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Book} from '../../../core/model/book';

describe('SearchCardComponent', () => {
  let component: SearchCardComponent;
  let fixture: ComponentFixture<SearchCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SearchCardComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        HttpClientTestingModule,
        MatCardModule,
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
      ],
      providers: [

        EndPointService,
        HttpClient,
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
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('test search', fakeAsync(inject([BookSearchService], (bookSearchService) => {
    let bookSearchServiceStub = spyOn(bookSearchService, 'search');
    let searchCompleteStub = spyOn(component.searchComplete, 'emit');
    let searchResult = Observable.of([new Book(), new Book()]);
    bookSearchServiceStub.and.returnValue(searchResult);
    component.search();
    tick();
    expect(searchCompleteStub.calls.count() == 1).toBeTruthy();
    expect(searchCompleteStub.calls.argsFor(0)[0].length == 2).toBeTruthy();

  })));
});
