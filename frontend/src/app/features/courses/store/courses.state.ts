import { Course } from '../models/course.model';

export interface CoursesState {
  courses: Course[];
  selectedCourse: Course | null;
  loading: boolean;
  error: string | null;
  filters: {
    category: string;
    sortOrder: string;
    searchTerm: string;
  };
}

export const initialState: CoursesState = {
  courses: [],
  selectedCourse: null,
  loading: false,
  error: null,
  filters: {
    category: 'Todos',
    sortOrder: 'asc',
    searchTerm: ''
  }
}; 