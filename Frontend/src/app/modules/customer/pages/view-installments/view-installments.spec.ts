import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewInstallments } from './view-installments';

describe('ViewInstallments', () => {
  let component: ViewInstallments;
  let fixture: ComponentFixture<ViewInstallments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewInstallments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewInstallments);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
