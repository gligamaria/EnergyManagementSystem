import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceDetailRestrictedComponent } from './device-detail-restricted.component';

describe('DeviceDetailRestrictedComponent', () => {
  let component: DeviceDetailRestrictedComponent;
  let fixture: ComponentFixture<DeviceDetailRestrictedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeviceDetailRestrictedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeviceDetailRestrictedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
