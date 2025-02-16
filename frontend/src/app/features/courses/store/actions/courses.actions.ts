import { createAction, props } from '@ngrx/store';
import { Course } from '../../models/course.model';

export const loadCourses = createAction('[Courses] Load Courses');
export const loadCoursesSuccess = createAction(
  '[Courses] Load Courses Success',
  props<{ courses: Course[] }>()
);
export const loadCoursesFailure = createAction(
  '[Courses] Load Courses Failure',
  props<{ error: string }>()
);

export const setCategoryFilter = createAction(
  '[Courses] Set Category Filter',
  props<{ category: string }>()
);

export const setSortOrder = createAction(
  '[Courses] Set Sort Order',
  props<{ sortOrder: string }>()
);

export const setSearchTerm = createAction(
  '[Courses] Set Search Term',
  props<{ searchTerm: string }>()
); 