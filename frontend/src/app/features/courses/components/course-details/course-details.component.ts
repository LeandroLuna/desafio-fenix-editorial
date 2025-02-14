import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Observable } from 'rxjs/internal/Observable';
import { Course } from '../../models/course.model';
import { ActivatedRoute } from '@angular/router';
import { CoursesMockService } from '../../services/courses-mock.service';

@Component({
  selector: 'app-course-details',
  standalone: false,
  templateUrl: './course-details.component.html',
  styleUrl: './course-details.component.scss'
})
export class CourseDetailsComponent implements OnInit {
  course$!: Observable<Course | undefined>;

  constructor(
    private route: ActivatedRoute,
    private coursesMockService: CoursesMockService,
    private location: Location
  ) {}

  ngOnInit(): void {
    const courseId = Number(this.route.snapshot.paramMap.get('id'));
    this.course$ = this.coursesMockService.getCourseById(courseId);
  }

  goBack(): void {
    this.location.back();
  }
}
