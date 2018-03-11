import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchResultCardComponent} from './search-result-card.component';
import {Router, RouterModule} from '@angular/router';
import {MatCardModule} from '@angular/material';
import {EndPointService} from '../../../core/service/end-point.service';
import {BASE_URL, REST_URL} from '../../../config';
import {APP_BASE_HREF} from '@angular/common';
import {Book} from '../../../core/model/book';

describe('SearchResultCardComponent', () => {
  let component: SearchResultCardComponent;
  let fixture: ComponentFixture<SearchResultCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SearchResultCardComponent],
      providers: [
        EndPointService,
        {
          provide: REST_URL,
          useFactory: () => {
            return {
              'Book': {
                url: '/book/{isbn}',
                needAuth: false,
                type: ['GET']
              },
              'Journal': {
                url: '/journal/{issn}/{year}/{issue}',
                needAuth: false,
                type: ['GET']
              },
              'Audio': {
                url: '/audio/{isrc}',
                needAuth: false,
                type: ['GET']
              }
            };
          },

        },
        {provide: BASE_URL, useValue: 'http://localhost'},
        {provide: APP_BASE_HREF, useValue: 'http://localhost'}
      ],
      imports: [
        RouterModule,
        MatCardModule,
        RouterModule.forRoot([])
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchResultCardComponent);
    component = fixture.componentInstance;
    let book = new Book();
    book.title = "testTitle";
    book.isbn = 1290238;
    component.publication = book;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
