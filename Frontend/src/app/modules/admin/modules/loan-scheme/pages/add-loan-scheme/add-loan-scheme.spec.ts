import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddLoanScheme } from './add-loan-scheme';

describe('AddLoanScheme', () => {
  let component: AddLoanScheme;
  let fixture: ComponentFixture<AddLoanScheme>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddLoanScheme]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddLoanScheme);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
