import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, switchMap, catchError } from 'rxjs/operators';
import { CoursesMockService } from '../../services/courses-mock.service';
import * as CoursesActions from '../actions/courses.actions';

@Injectable()
export class CoursesEffects {
  private actions$ = inject(Actions);
  private coursesService = inject(CoursesMockService);

  loadCourses$ = createEffect(() => 
    this.actions$.pipe(
      ofType(CoursesActions.loadCourses),
      switchMap(() => 
        this.coursesService.getCourses().pipe(
          map(courses => CoursesActions.loadCoursesSuccess({ courses })),
          catchError(error => of(CoursesActions.loadCoursesFailure({ error: error.message })))
        )
      )
    )
  );
}