import { Component, EventEmitter, Output } from '@angular/core';
import { MaterialModule } from '../../material.module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  imports: [MaterialModule, FormsModule],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.scss'
})
export class SearchBarComponent {
  searchTerm: string = ''; 

  @Output() search = new EventEmitter<string>();

  constructor() {}

  onSearch(event: Event | string): void {
    if (typeof event === 'string') {
      this.search.emit(event);
    } else {
      const input = event.target as HTMLInputElement;
      this.search.emit(input.value);
    }
  }
}
