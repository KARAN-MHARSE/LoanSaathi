import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckEligibility } from './check-eligibility';

describe('CheckEligibility', () => {
  let component: CheckEligibility;
  let fixture: ComponentFixture<CheckEligibility>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CheckEligibility]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CheckEligibility);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
