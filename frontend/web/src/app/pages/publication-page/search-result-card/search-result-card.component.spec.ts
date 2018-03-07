import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchResultCardComponent } from './search-result-card.component';

describe('SearchResultCardComponent', () => {
  let component: SearchResultCardComponent;
  let fixture: ComponentFixture<SearchResultCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchResultCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchResultCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
