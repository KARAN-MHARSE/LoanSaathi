import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanProduct } from './loan-product';

describe('LoanProduct', () => {
  let component: LoanProduct;
  let fixture: ComponentFixture<LoanProduct>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanProduct]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanProduct);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
