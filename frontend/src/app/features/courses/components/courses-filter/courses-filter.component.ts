import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-courses-filter',
  standalone: false,
  templateUrl: './courses-filter.component.html',
  styleUrl: './courses-filter.component.scss'
})
export class CoursesFilterComponent {
  @Output() filterCategory = new EventEmitter<string>();
  @Output() sortDuration = new EventEmitter<string>();

  categories: string[] = ['Todos', 'Programação', 'Design', 'Marketing'];
  sortOptions: { value: string; label: string }[] = [
    { value: 'asc', label: 'Duração: Menor para Maior' },
    { value: 'desc', label: 'Duração: Maior para Menor' }
  ];

  constructor() {}

  onCategoryChange(category: string): void {
    this.filterCategory.emit(category);
  }

  onSortChange(sort: string): void {
    this.sortDuration.emit(sort);
  }
}
