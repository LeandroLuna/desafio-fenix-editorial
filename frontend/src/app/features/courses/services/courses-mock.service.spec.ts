import { TestBed } from '@angular/core/testing';
import { CoursesMockService } from './courses-mock.service';
import { PLATFORM_ID } from '@angular/core';
import { firstValueFrom } from 'rxjs';

describe('CoursesMockService', () => {
  let service: CoursesMockService;

  describe('Browser Environment', () => {
    beforeEach(() => {
      TestBed.configureTestingModule({
        providers: [
          CoursesMockService,
          { provide: PLATFORM_ID, useValue: 'browser' }
        ]
      });
      service = TestBed.inject(CoursesMockService);
    });

    it('should be created in browser', () => {
      expect(service).toBeTruthy();
    });

    it('should return courses with delay in browser', async () => {
      const startTime = Date.now();
      const courses = await firstValueFrom(service.getCourses());
      const endTime = Date.now();
      
      expect(courses.length).toBeGreaterThan(0);
      expect(endTime - startTime).toBeGreaterThanOrEqual(300);
    });

    it('should return course by id with delay in browser', async () => {
      const startTime = Date.now();
      const course = await firstValueFrom(service.getCourseById(1));
      const endTime = Date.now();
      
      expect(course).toBeTruthy();
      expect(course?.id).toBe(1);
      expect(endTime - startTime).toBeGreaterThanOrEqual(300);
    });
  });

  describe('Server Environment', () => {
    beforeEach(() => {
      TestBed.configureTestingModule({
        providers: [
          CoursesMockService,
          { provide: PLATFORM_ID, useValue: 'server' }
        ]
      });
      service = TestBed.inject(CoursesMockService);
    });

    it('should return courses immediately in server', async () => {
      const startTime = Date.now();
      const courses = await firstValueFrom(service.getCourses());
      const endTime = Date.now();
      
      expect(courses.length).toBeGreaterThan(0);
      expect(endTime - startTime).toBeLessThan(300);
    });
  });
});
