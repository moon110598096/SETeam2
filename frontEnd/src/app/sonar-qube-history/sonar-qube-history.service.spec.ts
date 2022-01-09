import { TestBed } from '@angular/core/testing';

import { SonarQubeHistoryService } from './sonar-qube-history.service';

describe('SonarQubeHistoryService', () => {
  let service: SonarQubeHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SonarQubeHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
