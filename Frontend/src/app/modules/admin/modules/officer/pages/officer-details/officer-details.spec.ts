import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficerDetails } from './officer-details';

describe('OfficerDetails', () => {
  let component: OfficerDetails;
  let fixture: ComponentFixture<OfficerDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OfficerDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfficerDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
