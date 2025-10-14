import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateOfficerDetails } from './update-officer-details';

describe('UpdateOfficerDetails', () => {
  let component: UpdateOfficerDetails;
  let fixture: ComponentFixture<UpdateOfficerDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateOfficerDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateOfficerDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
