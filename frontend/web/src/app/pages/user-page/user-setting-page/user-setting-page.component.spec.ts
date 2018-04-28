import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSettingPageComponent } from './user-setting-page.component';

describe('UserSettingPageComponent', () => {
  let component: UserSettingPageComponent;
  let fixture: ComponentFixture<UserSettingPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserSettingPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSettingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
