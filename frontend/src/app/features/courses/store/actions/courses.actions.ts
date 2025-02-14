import { createAction, props } from '@ngrx/store';
import { Course } from '../../models/course.model';

export const loadCourses = createAction('[Courses] Load Courses');
export const loadCoursesSuccess = createAction('[Courses] Load Courses Success', props<{ courses: Course[] }>());
export const loadCoursesFailure = createAction('[Courses] Load Courses Failure', props<{ error: any }>());