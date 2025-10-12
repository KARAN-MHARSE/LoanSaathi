import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationSubmit } from './application-submit';

describe('ApplicationSubmit', () => {
  let component: ApplicationSubmit;
  let fixture: ComponentFixture<ApplicationSubmit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApplicationSubmit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplicationSubmit);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
