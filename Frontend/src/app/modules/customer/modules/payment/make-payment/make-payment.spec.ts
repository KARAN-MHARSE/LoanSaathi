import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MakePayment } from './make-payment';

describe('MakePayment', () => {
  let component: MakePayment;
  let fixture: ComponentFixture<MakePayment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MakePayment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MakePayment);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
