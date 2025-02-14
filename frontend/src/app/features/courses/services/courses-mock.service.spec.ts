import { TestBed } from '@angular/core/testing';

import { CoursesMockService } from './courses-mock.service';

describe('CoursesMockService', () => {
  let service: CoursesMockService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CoursesMockService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
