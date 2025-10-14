import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppliedApplicationDetails } from './applied-application-details';

describe('AppliedApplicationDetails', () => {
  let component: AppliedApplicationDetails;
  let fixture: ComponentFixture<AppliedApplicationDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppliedApplicationDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppliedApplicationDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
