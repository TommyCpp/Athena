import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginDialogComponent} from './login-dialog.component';
import {
  MAT_DIALOG_DATA,
  MatButtonModule, MatDialog,
  MatDialogContainer,
  MatDialogModule,
  MatDialogRef,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LoginService} from '../../core/service/login.service';
import {OverlayRef} from '@angular/cdk/overlay';
import {Observable} from 'rxjs/Observable';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LoginDialogComponentTestModule} from './login-dialog.test.module';
import {By} from '@angular/platform-browser';

class MockLoginService extends LoginService {

  public login(username: string, password: string): Observable<any> {
    return null;
  }
}


describe('LoginDialogComponent', () => {
  let component: LoginDialogComponent;
  let fixture: ComponentFixture<LoginDialogComponent>;

  beforeEach(async(() => {
      TestBed.configureTestingModule({
        providers: [
          {provide: MAT_DIALOG_DATA, useValue: null},
          {provide: LoginService, useValue: MockLoginService},
          {
            provide: MatDialogRef,
            useFactory: (dialog: MatDialog) => {
              return dialog.open(LoginDialogComponent, {
                disableClose: true, // can only close by click close button
              });
            },
            deps: [MatDialog]
          },
        ],
        imports: [
          LoginDialogComponentTestModule
        ]
      })
        .compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should has "Login" as title', () => {
    const title = fixture.debugElement.query(By.css('h2'));
    expect(title.nativeElement.textContent).toEqual('Login');
  });
})
;
