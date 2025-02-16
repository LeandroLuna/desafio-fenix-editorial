import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../shared/material.module';

import { CoursesListComponent } from './components/courses-list/courses-list.component';
import { CourseDetailsComponent } from './components/course-details/course-details.component';
import { CoursesRoutingModule } from './courses-routing.module';
import { CoursesFilterComponent } from './components/courses-filter/courses-filter.component';
import { CoursesPageComponent } from './pages/courses-page/courses-page.component';
import { SearchBarComponent } from '../../shared/components/search-bar/search-bar.component';

@NgModule({
  declarations: [
    CoursesPageComponent,
    CoursesListComponent,
    CourseDetailsComponent,
    CoursesFilterComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    CoursesRoutingModule,
    SearchBarComponent  
  ]
})
export class CoursesModule { }
