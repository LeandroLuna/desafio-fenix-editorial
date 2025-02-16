import { createFeatureSelector, createSelector } from '@ngrx/store';
import { CoursesState } from '../courses.state';

export const selectCoursesState = createFeatureSelector<CoursesState>('courses');

export const selectAllCourses = createSelector(
  selectCoursesState,
  (state) => state.courses
);

export const selectFilters = createSelector(
  selectCoursesState,
  (state) => state.filters
);

export const selectFilteredCourses = createSelector(
  selectAllCourses,
  selectFilters,
  (courses, filters) => {
    let filteredCourses = courses.filter(course =>
      course.name.toLowerCase().includes(filters.searchTerm.toLowerCase())
    );

    if (filters.category !== 'Todos') {
      filteredCourses = filteredCourses.filter(course => 
        course.category === filters.category
      );
    }

    return filteredCourses.sort((a, b) => 
      filters.sortOrder === 'asc' ? 
        a.duration - b.duration : 
        b.duration - a.duration
    );
  }
);

export const selectLoading = createSelector(
  selectCoursesState,
  (state) => state.loading
);

export const selectError = createSelector(
  selectCoursesState,
  (state) => state.error
);

export const selectCategory = createSelector(
  selectFilters,
  (filters) => filters.category || 'Todos'
);

export const selectSearchTerm = createSelector(
  selectFilters,
  (filters) => filters.searchTerm || ''
);

export const selectSortOrder = createSelector(
  selectFilters,
  (filters) => filters.sortOrder || 'desc'
);