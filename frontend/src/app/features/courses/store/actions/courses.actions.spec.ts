import * as CoursesActions from './courses.actions';
import { Course } from '../../models/course.model';

describe('Courses Actions', () => {
  const mockCourses: Course[] = [{
    id: 1,
    name: 'Angular',
    description: 'Aprenda Angular',
    category: 'Programação',
    duration: 10,
    image: 'https://placehold.co/400x200?text=Angular',
    lessons: []
  }];

  it('should create loadCourses action', () => {
    const action = CoursesActions.loadCourses();
    expect(action.type).toBe('[Courses] Load Courses');
  });

  it('should create loadCoursesSuccess action', () => {
    const action = CoursesActions.loadCoursesSuccess({ courses: mockCourses });
    expect(action.type).toBe('[Courses] Load Courses Success');
    expect(action.courses).toEqual(mockCourses);
  });

  it('should create loadCoursesFailure action', () => {
    const error = 'Error loading courses';
    const action = CoursesActions.loadCoursesFailure({ error });
    expect(action.type).toBe('[Courses] Load Courses Failure');
    expect(action.error).toBe(error);
  });

  it('should create setCategoryFilter action', () => {
    const category = 'Programação';
    const action = CoursesActions.setCategoryFilter({ category });
    expect(action.type).toBe('[Courses] Set Category Filter');
    expect(action.category).toBe(category);
  });

  it('should create setSortOrder action', () => {
    const sortOrder = 'desc';
    const action = CoursesActions.setSortOrder({ sortOrder });
    expect(action.type).toBe('[Courses] Set Sort Order');
    expect(action.sortOrder).toBe(sortOrder);
  });

  it('should create setSearchTerm action', () => {
    const searchTerm = 'Angular';
    const action = CoursesActions.setSearchTerm({ searchTerm });
    expect(action.type).toBe('[Courses] Set Search Term');
    expect(action.searchTerm).toBe(searchTerm);
  });
}); 