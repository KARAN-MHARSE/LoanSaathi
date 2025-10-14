import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllLoanSchemes } from './all-loan-schemes';

describe('AllLoanSchemes', () => {
  let component: AllLoanSchemes;
  let fixture: ComponentFixture<AllLoanSchemes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllLoanSchemes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllLoanSchemes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
