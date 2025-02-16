import { coursesReducer } from './courses.reducers';
import * as CoursesActions from '../actions/courses.actions';
import { initialState } from '../courses.state';
import { Course } from '../../models/course.model';

describe('Courses Reducer', () => {
  const mockCourses: Course[] = [{
    id: 1,
    name: 'Angular',
    description: 'Aprenda Angular',
    category: 'Programação',
    duration: 10,
    image: 'https://placehold.co/400x200?text=Angular',
    lessons: []
  }];

  it('should return the default state', () => {
    const action = { type: 'NOOP' } as any;
    const state = coursesReducer(undefined, action);
    expect(state).toBe(initialState);
  });

  it('should set loading true on loadCourses', () => {
    const action = CoursesActions.loadCourses();
    const state = coursesReducer(initialState, action);
    expect(state.loading).toBe(true);
    expect(state.error).toBeNull();
  });

  it('should update courses on loadCoursesSuccess', () => {
    const action = CoursesActions.loadCoursesSuccess({ courses: mockCourses });
    const state = coursesReducer(initialState, action);
    expect(state.courses).toEqual(mockCourses);
    expect(state.loading).toBe(false);
    expect(state.error).toBeNull();
  });

  it('should set error on loadCoursesFailure', () => {
    const error = 'Error loading courses';
    const action = CoursesActions.loadCoursesFailure({ error });
    const state = coursesReducer(initialState, action);
    expect(state.error).toBe(error);
    expect(state.loading).toBe(false);
  });

  it('should update category filter', () => {
    const category = 'Programação';
    const action = CoursesActions.setCategoryFilter({ category });
    const state = coursesReducer(initialState, action);
    expect(state.filters.category).toBe(category);
  });

  it('should update sort order', () => {
    const sortOrder = 'desc';
    const action = CoursesActions.setSortOrder({ sortOrder });
    const state = coursesReducer(initialState, action);
    expect(state.filters.sortOrder).toBe(sortOrder);
  });

  it('should update search term', () => {
    const searchTerm = 'Angular';
    const action = CoursesActions.setSearchTerm({ searchTerm });
    const state = coursesReducer(initialState, action);
    expect(state.filters.searchTerm).toBe(searchTerm);
  });
}); 