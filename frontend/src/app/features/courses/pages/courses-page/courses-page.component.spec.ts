import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CoursesPageComponent } from './courses-page.component';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { Course } from '../../models/course.model';
import * as CoursesActions from '../../store/actions/courses.actions';
import * as CoursesSelectors from '../../store/selectors/courses.selectors';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../../../../shared/material.module';
import { CoursesListComponent } from '../../components/courses-list/courses-list.component';
import { CoursesFilterComponent } from '../../components/courses-filter/courses-filter.component';
import { SearchBarComponent } from '../../../../shared/components/search-bar/search-bar.component';
import { initialState } from '../../store/courses.state';
import { provideRouter } from '@angular/router';
import { RouterModule } from '@angular/router';

describe('CoursesPageComponent', () => {
  let component: CoursesPageComponent;
  let fixture: ComponentFixture<CoursesPageComponent>;
  let store: MockStore;

  const mockCourses: Course[] = [
    {
      id: 1,
      name: 'Angular',
      description: 'Aprenda Angular',
      category: 'Programação',
      duration: 10,
      image: 'https://placehold.co/400x200?text=Angular',
      lessons: []
    }
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        CoursesPageComponent,
        CoursesListComponent,
        CoursesFilterComponent
      ],
      imports: [
        RouterModule,
        NoopAnimationsModule,
        MaterialModule,
        SearchBarComponent
      ],
      providers: [
        provideMockStore({ initialState }),
        provideRouter([])
      ]
    }).compileComponents();

    store = TestBed.inject(MockStore);
    store.overrideSelector(CoursesSelectors.selectFilteredCourses, mockCourses);
    store.overrideSelector(CoursesSelectors.selectLoading, false);
    store.overrideSelector(CoursesSelectors.selectError, null);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CoursesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should dispatch loadCourses on init', () => {
    const spy = spyOn(store, 'dispatch');
    component.ngOnInit();
    expect(spy).toHaveBeenCalledWith(CoursesActions.loadCourses());
  });

  it('should dispatch setCategoryFilter when category changes', () => {
    const spy = spyOn(store, 'dispatch');
    const category = 'Programação';
    component.onCategoryChange(category);
    expect(spy).toHaveBeenCalledWith(CoursesActions.setCategoryFilter({ category }));
  });

  it('should dispatch setSortOrder when sort order changes', () => {
    const spy = spyOn(store, 'dispatch');
    const sortOrder = 'desc';
    component.onSortChange(sortOrder);
    expect(spy).toHaveBeenCalledWith(CoursesActions.setSortOrder({ sortOrder }));
  });

  it('should dispatch setSearchTerm when search term changes', () => {
    const spy = spyOn(store, 'dispatch');
    component.onSearch('Angular');
    expect(spy).toHaveBeenCalledWith(jasmine.objectContaining({
      type: '[Courses] Set Search Term'
    }));
  });
});
