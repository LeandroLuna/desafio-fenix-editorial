import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CoursesModule } from './courses.module';
import { CoursesPageComponent } from './pages/courses-page/courses-page.component';
import { provideMockStore } from '@ngrx/store/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { initialState } from './store/courses.state';
import { Course } from './models/course.model';
import { By } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

describe('CoursesModule Integration', () => {
  let component: CoursesPageComponent;
  let fixture: ComponentFixture<CoursesPageComponent>;

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
      imports: [
        CoursesModule,
        RouterModule,
        NoopAnimationsModule
      ],
      providers: [
        provideRouter([]),
        provideMockStore({
          initialState: {
            courses: {
              ...initialState,
              courses: mockCourses
            }
          }
        })
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CoursesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the courses page component', () => {
    expect(component).toBeTruthy();
  });

  it('should render the courses list component', () => {
    fixture.detectChanges();
    const coursesList = fixture.debugElement.query(By.css('app-courses-list'));
    expect(coursesList).toBeTruthy();
  });

  it('should render the courses filter component', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('app-courses-filter')).toBeTruthy();
  });

  it('should render the search bar component', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('app-search-bar')).toBeTruthy();
  });
}); 