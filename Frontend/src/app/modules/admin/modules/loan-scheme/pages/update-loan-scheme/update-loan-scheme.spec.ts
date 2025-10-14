import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateLoanScheme } from './update-loan-scheme';

describe('UpdateLoanScheme', () => {
  let component: UpdateLoanScheme;
  let fixture: ComponentFixture<UpdateLoanScheme>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateLoanScheme]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateLoanScheme);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
