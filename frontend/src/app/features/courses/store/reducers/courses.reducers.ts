import { createReducer, on } from '@ngrx/store';
import { initialState } from '../courses.state';
import * as CoursesActions from '../actions/courses.actions';

export const coursesReducer = createReducer(
    initialState,
    on(CoursesActions.loadCourses, (state) => ({
        ...state,
        loading: true
    })),
    on(CoursesActions.loadCoursesSuccess, (state, { courses }) => ({
        ...state,
        loading: false,
        courses,
        error: null
    })),
    on(CoursesActions.loadCoursesFailure, (state, { error }) => ({
        ...state,
        loading: false,
        error
    })),
    on(CoursesActions.setCategoryFilter, (state, { category }) => ({
        ...state,
        filters: {
        ...state.filters,
        category
        }
    })),
    on(CoursesActions.setSortOrder, (state, { sortOrder }) => ({
        ...state,
        filters: {
        ...state.filters,
        sortOrder
        }
    })),
    on(CoursesActions.setSearchTerm, (state, { searchTerm }) => ({
        ...state,
        filters: {
        ...state.filters,
        searchTerm
        }
    }))
);