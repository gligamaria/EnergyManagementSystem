import { TestBed } from '@angular/core/testing';

import { HourlyConsumptionService } from './hourly-consumption.service';

describe('HourlyConsumptionService', () => {
  let service: HourlyConsumptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HourlyConsumptionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
