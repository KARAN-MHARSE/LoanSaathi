import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanSchemeDetail } from './loan-scheme-detail';

describe('LoanSchemeDetail', () => {
  let component: LoanSchemeDetail;
  let fixture: ComponentFixture<LoanSchemeDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanSchemeDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanSchemeDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
