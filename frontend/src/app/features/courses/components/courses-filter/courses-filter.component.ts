import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-courses-filter',
  standalone: false,
  templateUrl: './courses-filter.component.html',
  styleUrl: './courses-filter.component.scss'
})
export class CoursesFilterComponent {
  @Output() categoryChange = new EventEmitter<string>();
  @Output() sortChange = new EventEmitter<string>();

  categories: string[] = ['Todos', 'Programação', 'Design', 'Marketing'];
  sortOptions: { value: string; label: string }[] = [
    { value: 'asc', label: 'Duração: Menor para Maior' },
    { value: 'desc', label: 'Duração: Maior para Menor' }
  ];

  constructor() {}

  onCategoryChange(category: string): void {
    this.categoryChange.emit(category);
  }

  onSortChange(sort: string): void {
    this.sortChange.emit(sort);
  }
}
