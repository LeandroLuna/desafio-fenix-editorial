import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-courses-filter',
  standalone: false,
  templateUrl: './courses-filter.component.html',
  styleUrl: './courses-filter.component.scss'
})
export class CoursesFilterComponent implements OnInit {
  @Input() initialCategory: string = 'Todos';
  @Input() initialSortOrder: string = 'desc';
  @Output() categoryChange = new EventEmitter<string>();
  @Output() sortChange = new EventEmitter<string>();

  selectedCategory: string = 'Todos';
  selectedSortOrder: string = 'desc';

  categories: string[] = ['Todos', 'Programação', 'Design', 'Marketing'];
  sortOptions: { value: string; label: string }[] = [
    { value: 'asc', label: 'Duração: Menor para Maior' },
    { value: 'desc', label: 'Duração: Maior para Menor' }
  ];

  ngOnInit() {
    if (this.initialCategory) {
      this.selectedCategory = this.initialCategory;
    }
    if (this.initialSortOrder) {
      this.selectedSortOrder = this.initialSortOrder;
    }
  }

  onCategoryChange(category: string): void {
    this.selectedCategory = category;
    this.categoryChange.emit(category);
  }

  onSortChange(sort: string): void {
    this.selectedSortOrder = sort;
    this.sortChange.emit(sort);
  }
}
