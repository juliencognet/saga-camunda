import { TestBed } from '@angular/core/testing';

import { OrchestratorControllerService } from './orchestrator-controller.service';

describe('OrchestratorControllerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OrchestratorControllerService = TestBed.get(OrchestratorControllerService);
    expect(service).toBeTruthy();
  });
});
