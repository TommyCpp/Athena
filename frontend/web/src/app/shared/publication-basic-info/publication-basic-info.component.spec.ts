import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicationBasicInfoComponent } from './publication-basic-info.component';

describe('PublicationBasicInfoComponent', () => {
  let component: PublicationBasicInfoComponent;
  let fixture: ComponentFixture<PublicationBasicInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublicationBasicInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PublicationBasicInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
