import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginDialogComponent} from './login-dialog.component';
import {ATHENA_ROUTES} from '../../routes';
import {RouterModule} from '@angular/router';
import {MatDialogModule} from '@angular/material';

describe('LoginDialogComponent', () => {
  let component: LoginDialogComponent;
  let fixture: ComponentFixture<LoginDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LoginDialogComponent],
      imports: [MatDialogModule],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
