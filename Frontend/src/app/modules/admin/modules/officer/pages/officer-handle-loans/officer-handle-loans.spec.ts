import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficerHandleLoans } from './officer-handle-loans';

describe('OfficerHandleLoans', () => {
  let component: OfficerHandleLoans;
  let fixture: ComponentFixture<OfficerHandleLoans>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OfficerHandleLoans]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfficerHandleLoans);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
