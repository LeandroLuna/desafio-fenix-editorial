import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'courses',
    loadChildren: () => import('./features/courses/courses.module').then(m => m.CoursesModule)
  },
  {
    path: '',
    redirectTo: '/courses',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '/courses'
  }
];

export { routes };