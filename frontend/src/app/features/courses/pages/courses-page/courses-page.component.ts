import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Course } from '../../models/course.model';
import * as CoursesActions from '../../store/actions/courses.actions';
import * as CoursesSelectors from '../../store/selectors/courses.selectors';

@Component({
  selector: 'app-courses-page',
  standalone: false,
  templateUrl: './courses-page.component.html',
  styleUrl: './courses-page.component.scss'
})
export class CoursesPageComponent implements OnInit {
  courses$: Observable<Course[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;
  currentCategory$: Observable<string>;
  currentSearchTerm$: Observable<string>;
  currentSortOrder$: Observable<string>;

  constructor(private store: Store) {
    this.courses$ = this.store.select(CoursesSelectors.selectFilteredCourses);
    this.loading$ = this.store.select(CoursesSelectors.selectLoading);
    this.error$ = this.store.select(CoursesSelectors.selectError);
    this.currentCategory$ = this.store.select(CoursesSelectors.selectCategory);
    this.currentSearchTerm$ = this.store.select(CoursesSelectors.selectSearchTerm);
    this.currentSortOrder$ = this.store.select(CoursesSelectors.selectSortOrder);
  }

  ngOnInit(): void {
    this.store.dispatch(CoursesActions.loadCourses());
  }

  onCategoryChange(category: string): void {
    this.store.dispatch(CoursesActions.setCategoryFilter({ category }));
  }

  onSortChange(sortOrder: string): void {
    this.store.dispatch(CoursesActions.setSortOrder({ sortOrder }));
  }

  onSearch(searchTerm: string): void {
    this.store.dispatch(CoursesActions.setSearchTerm({ searchTerm }));
  }
}
