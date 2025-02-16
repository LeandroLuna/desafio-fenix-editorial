import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { Course } from '../models/course.model';
import { delay, Observable, of } from 'rxjs';
import { isPlatformServer } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class CoursesMockService {
  private platformId = inject(PLATFORM_ID);
  
  private mockCourses: Course[] = [
    {
      id: 1,
      name: 'Angular Avançado',
      description: 'Aprofunde seus conhecimentos em Angular.',
      category: 'Programação',
      duration: 30,
      image: 'https://placehold.co/400x200?text=Angular+Avançado',
      lessons: [
        { title: 'Introdução ao Angular', duration: 2 },
        { title: 'Componentes Avançados', duration: 3 },
        { title: 'Gerenciamento de Estado', duration: 4 }
      ]
    },
    {
      id: 2,
      name: 'Design UI/UX',
      description: 'Aprenda as melhores práticas de design.',
      category: 'Design',
      duration: 20,
      image: 'https://placehold.co/400x200?text=Design+UI/UX',
      lessons: [
        { title: 'Princípios de Design', duration: 2 },
        { title: 'Prototipagem com Figma', duration: 4 },
        { title: 'Testes de Usabilidade', duration: 3 }
      ]
    },
    {
      id: 3,
      name: 'Marketing Digital',
      description: 'Domine estratégias de marketing digital.',
      category: 'Marketing',
      duration: 15,
      image: 'https://placehold.co/400x200?text=Marketing+Digital',
      lessons: [
        { title: 'Introdução ao Marketing Digital', duration: 1 },
        { title: 'SEO e SEM', duration: 2 },
        { title: 'Marketing de Conteúdo', duration: 3 }
      ]
    },
    {
      id: 4,
      name: 'React para Iniciantes',
      category: 'Programação',
      description: 'Aprenda React do zero.',
      duration: 10,
      image: 'https://placehold.co/400x200?text=React+para+Iniciantes',
      lessons: [
        { title: 'Introdução ao React', duration: 2 },
        { title: 'Hooks e Context API', duration: 3 },
        { title: 'Consumindo APIs', duration: 2 }
      ]
    },
    {
      id: 5,
      name: 'Experiência do Usuário',
      category: 'Design',
      description: 'Construa interfaces incríveis.',
      duration: 25,
      image: 'https://placehold.co/400x200?text=Experiência+do+Usuário',
      lessons: [
        { title: 'Introdução à UX', duration: 2 },
        { title: 'Wireframes e Protótipos', duration: 4 },
        { title: 'Testes de Usabilidade', duration: 3 }
      ]
    },
    {
      id: 6,
      name: 'Growth Hacking',
      category: 'Marketing',
      description: 'Aprenda estratégias de crescimento.',
      duration: 12,
      image: 'https://placehold.co/400x200?text=Growth+Hacking',
      lessons: [
        { title: 'Introdução ao Growth Hacking', duration: 2 },
        { title: 'Métricas e Analytics', duration: 3 },
        { title: 'Testes A/B', duration: 2 }
      ]
    }
  ];

  constructor() { }

  getCourses(): Observable<Course[]> {
    if (isPlatformServer(this.platformId)) {
      return of(this.mockCourses);
    }
    return of(this.mockCourses).pipe(delay(300));
  }

  getCourseById(id: number): Observable<Course | undefined> {
    const course = this.mockCourses.find(c => c.id === id);
    if (isPlatformServer(this.platformId)) {
      return of(course);
    }
    return of(course).pipe(delay(300));
  }
}
