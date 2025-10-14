import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanSchems } from './loan-schems';

describe('LoanSchems', () => {
  let component: LoanSchems;
  let fixture: ComponentFixture<LoanSchems>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanSchems]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanSchems);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
