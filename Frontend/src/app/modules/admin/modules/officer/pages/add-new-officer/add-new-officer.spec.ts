import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewOfficer } from './add-new-officer';

describe('AddNewOfficer', () => {
  let component: AddNewOfficer;
  let fixture: ComponentFixture<AddNewOfficer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddNewOfficer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNewOfficer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
