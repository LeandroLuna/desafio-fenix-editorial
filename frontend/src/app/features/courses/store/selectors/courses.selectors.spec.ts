import * as CoursesSelectors from './courses.selectors';
import { initialState } from '../courses.state';
import { Course } from '../../models/course.model';

describe('Courses Selectors', () => {
  const mockCourses: Course[] = [
    {
      id: 1,
      name: 'Angular',
      description: 'Aprenda Angular',
      category: 'Programação',
      duration: 10,
      image: 'https://placehold.co/400x200?text=Angular',
      lessons: []
    },
    {
      id: 2,
      name: 'React',
      description: 'Aprenda React',
      category: 'Programação',
      duration: 15,
      image: 'https://placehold.co/400x200?text=React',
      lessons: []
    }
  ];

  const baseState = {
    courses: {
      ...initialState,
      courses: mockCourses,
      filters: {
        category: 'Todos',
        sortOrder: 'asc',
        searchTerm: ''
      }
    }
  };

  it('should select all courses', () => {
    const result = CoursesSelectors.selectAllCourses(baseState);
    expect(result).toEqual(mockCourses);
  });

  it('should select loading state', () => {
    const result = CoursesSelectors.selectLoading(baseState);
    expect(result).toBe(false);
  });

  it('should select error state', () => {
    const result = CoursesSelectors.selectError(baseState);
    expect(result).toBeNull();
  });

  it('should select filtered courses by category', () => {
    const state = {
      courses: {
        ...baseState.courses,
        filters: { 
          ...baseState.courses.filters, 
          category: 'Programação'
        }
      }
    };
    const result = CoursesSelectors.selectFilteredCourses(state);
    expect(result.length).toBe(2);
    expect(result.map(c => c.name)).toEqual(['Angular', 'React']);
    expect(result.map(c => c.category)).toEqual(['Programação', 'Programação']);
  });

  it('should select courses sorted by name desc', () => {
    const state = {
      courses: {
        ...baseState.courses,
        filters: { 
          ...baseState.courses.filters, 
          sortOrder: 'desc',
          category: 'Todos'
        }
      }
    };
    const result = CoursesSelectors.selectFilteredCourses(state);
    expect(result.map(c => c.name)).toEqual(['React', 'Angular']);
  });

  it('should select courses filtered by search term', () => {
    const state = {
      courses: {
        ...baseState.courses,
        filters: { ...baseState.courses.filters, searchTerm: 'Angular' }
      }
    };
    const result = CoursesSelectors.selectFilteredCourses(state);
    expect(result[0].name).toContain('Angular');
  });
}); 