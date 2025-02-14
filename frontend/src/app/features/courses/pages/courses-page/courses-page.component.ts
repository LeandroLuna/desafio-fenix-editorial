import { Component } from '@angular/core';
import { BehaviorSubject, combineLatest, map, Observable, startWith } from 'rxjs';
import { Course } from '../../models/course.model';
import { CoursesMockService } from '../../services/courses-mock.service';

@Component({
  selector: 'app-courses-page',
  standalone: false,
  templateUrl: './courses-page.component.html',
  styleUrl: './courses-page.component.scss'
})
export class CoursesPageComponent {
  private categoryFilter$ = new BehaviorSubject<string>('Todos');
  private sortOption$ = new BehaviorSubject<string>('asc');
  searchTerm$ = new BehaviorSubject<string>('');

  courses$!: Observable<Course[]>;

  constructor(private coursesMockService: CoursesMockService) {}

  ngOnInit(): void {
    const allCourses$ = this.coursesMockService.getCourses().pipe(
      map(courses => courses ?? []),
      startWith([])
    );
  
    this.courses$ = combineLatest([allCourses$, this.categoryFilter$, this.sortOption$, this.searchTerm$]).pipe(
      map(([courses, category, sort, term]) => {
        let filteredCourses = courses.filter(course =>
          course.name.toLowerCase().includes(term.toLowerCase())
        );

        if (category !== 'Todos') {
          filteredCourses = filteredCourses.filter(course => course.category === category);
        }

        return filteredCourses.sort((a, b) => 
          sort === 'asc' ? a.duration - b.duration : b.duration - a.duration
        );
      })
    );
  }

  onCategoryChange(category: string): void {
    this.categoryFilter$.next(category);
  }

  onSortChange(sort: string): void {
    this.sortOption$.next(sort);
  }

  onSearch(term: string): void {
    this.searchTerm$.next(term);
  }
}
