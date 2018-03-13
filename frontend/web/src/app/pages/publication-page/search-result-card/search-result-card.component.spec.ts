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
                relativeUrl: '/book/{isbn}',
                needAuth: false,
                type: ['GET']
              },
              'Journal': {
                relativeUrl: '/journal/{issn}/{year}/{issue}',
                needAuth: false,
                type: ['GET']
              },
              'Audio': {
                relativeUrl: '/audio/{isrc}',
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

  it('should has title', () => {
    const element: HTMLElement = fixture.nativeElement;
    const title = element.querySelector('mat-card-title');
    expect(title.textContent).toBe("testTitle")
  });


  it('should has right detail link',()=>{
    const element: HTMLElement = fixture.nativeElement;
    const  link = element.querySelector('button > a');
    expect(link['href']).toBe("http://localhost/book/1290238")
  })
});
