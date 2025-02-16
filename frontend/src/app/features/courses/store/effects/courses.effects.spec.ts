import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable, of, throwError } from 'rxjs';
import { CoursesEffects } from './courses.effects';
import { CoursesMockService } from '../../services/courses-mock.service';
import * as CoursesActions from '../actions/courses.actions';
import { Course } from '../../models/course.model';
import { hot, cold } from 'jasmine-marbles';

describe('CoursesEffects', () => {
  let actions$: Observable<any>;
  let effects: CoursesEffects;
  let coursesService: jasmine.SpyObj<CoursesMockService>;

  const mockCourses: Course[] = [
    {
      id: 1,
      name: 'Angular',
      description: 'Aprenda Angular',
      category: 'Programação',
      duration: 10,
      image: 'https://placehold.co/400x200?text=Angular',
      lessons: []
    }
  ];

  beforeEach(() => {
    const spy = jasmine.createSpyObj('CoursesMockService', ['getCourses']);
    TestBed.configureTestingModule({
      providers: [
        CoursesEffects,
        provideMockActions(() => actions$),
        { provide: CoursesMockService, useValue: spy }
      ]
    });

    effects = TestBed.inject(CoursesEffects);
    coursesService = TestBed.inject(CoursesMockService) as jasmine.SpyObj<CoursesMockService>;
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });

  it('should load courses successfully', () => {
    coursesService.getCourses.and.returnValue(of(mockCourses));
    
    actions$ = hot('-a', { a: CoursesActions.loadCourses() });
    const expected = cold('-b', {
      b: CoursesActions.loadCoursesSuccess({ courses: mockCourses })
    });

    expect(effects.loadCourses$).toBeObservable(expected);
  });

  it('should handle error when loading courses fails', () => {
    const error = new Error('Test error');
    coursesService.getCourses.and.returnValue(throwError(() => error));
    
    actions$ = hot('-a', { a: CoursesActions.loadCourses() });
    const expected = cold('-b', {
      b: CoursesActions.loadCoursesFailure({ error: error.message })
    });

    expect(effects.loadCourses$).toBeObservable(expected);
  });
}); 