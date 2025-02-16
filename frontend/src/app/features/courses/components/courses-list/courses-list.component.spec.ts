import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CoursesListComponent } from './courses-list.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../../../../shared/material.module';
import { Course } from '../../models/course.model';
import { provideRouter, RouterModule } from '@angular/router';

describe('CoursesListComponent', () => {
  let component: CoursesListComponent;
  let fixture: ComponentFixture<CoursesListComponent>;

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
      declarations: [CoursesListComponent],
      imports: [
        RouterModule,
        NoopAnimationsModule,
        MaterialModule
      ],
      providers: [
        provideRouter([])
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CoursesListComponent);
    component = fixture.componentInstance;
    component.courses = mockCourses;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should display courses in cards', () => {
    const cards = fixture.nativeElement.querySelectorAll('mat-card');
    expect(cards.length).toBe(mockCourses.length);
  });

  it('should display course information correctly', () => {
    const card = fixture.nativeElement.querySelector('mat-card');
    expect(card.textContent).toContain('Angular');
    expect(card.textContent).toContain('Aprenda Angular');
    expect(card.textContent).toContain('Programação');
    expect(card.textContent).toContain('10 horas');
  });
});
