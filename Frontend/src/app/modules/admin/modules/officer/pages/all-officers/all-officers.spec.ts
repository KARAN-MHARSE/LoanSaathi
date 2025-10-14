import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllOfficers } from './all-officers';

describe('AllOfficers', () => {
  let component: AllOfficers;
  let fixture: ComponentFixture<AllOfficers>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllOfficers]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllOfficers);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
