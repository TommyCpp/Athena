import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicationPrefaceComponent } from './publication-preface.component';

describe('PublicationPrefaceComponent', () => {
  let component: PublicationPrefaceComponent;
  let fixture: ComponentFixture<PublicationPrefaceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublicationPrefaceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PublicationPrefaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
