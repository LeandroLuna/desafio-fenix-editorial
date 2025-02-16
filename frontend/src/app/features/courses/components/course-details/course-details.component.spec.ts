import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CourseDetailsComponent } from './course-details.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../../../../shared/material.module';
import { Course } from '../../models/course.model';
import { of } from 'rxjs';
import { CoursesMockService } from '../../services/courses-mock.service';
import { provideRouter } from '@angular/router';

describe('CourseDetailsComponent', () => {
  let component: CourseDetailsComponent;
  let fixture: ComponentFixture<CourseDetailsComponent>;
  let courseService: jasmine.SpyObj<CoursesMockService>;

  const mockCourse: Course = {
    id: 1,
    name: 'Angular',
    description: 'Aprenda Angular',
    category: 'Programação',
    duration: 10,
    image: 'https://placehold.co/400x200?text=Angular',
    lessons: [
      { title: 'Introdução', duration: 2 },
      { title: 'Componentes', duration: 3 }
    ]
  };

  beforeEach(async () => {
    courseService = jasmine.createSpyObj('CoursesMockService', ['getCourseById']);
    courseService.getCourseById.and.returnValue(of(mockCourse));

    await TestBed.configureTestingModule({
      declarations: [CourseDetailsComponent],
      imports: [
        NoopAnimationsModule,
        MaterialModule
      ],
      providers: [
        { provide: CoursesMockService, useValue: courseService }, 
        provideRouter([])
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CourseDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should display course details', () => {
    const element = fixture.nativeElement;
    expect(element.textContent).toContain('Angular');
    expect(element.textContent).toContain('Aprenda Angular');
    expect(element.textContent).toContain('Programação');
    expect(element.textContent).toContain('10 horas');
  });

  it('should display lesson list', () => {
    const lessons = fixture.nativeElement.querySelectorAll('mat-list-item');
    expect(lessons.length).toBe(mockCourse.lessons.length);
    expect(lessons[0].textContent).toContain('Introdução');
    expect(lessons[0].textContent).toContain('2h');
  });
});
