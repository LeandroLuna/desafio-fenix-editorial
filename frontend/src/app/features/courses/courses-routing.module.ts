import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CourseDetailsComponent } from './components/course-details/course-details.component';
import { CoursesPageComponent } from './pages/courses-page/courses-page.component';

const routes: Routes = [
  {
    path: '',
    component: CoursesPageComponent
  },
  {
    path: ':id',
    component: CourseDetailsComponent
  }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class CoursesRoutingModule { }
