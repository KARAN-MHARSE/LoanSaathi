import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewLoanApplication } from './new-loan-application';

describe('NewLoanApplication', () => {
  let component: NewLoanApplication;
  let fixture: ComponentFixture<NewLoanApplication>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewLoanApplication]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewLoanApplication);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
