import { TestBed } from '@angular/core/testing';

import { RestControllerService } from './rest-controller.service';

describe('RestControllerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RestControllerService = TestBed.get(RestControllerService);
    expect(service).toBeTruthy();
  });
});
