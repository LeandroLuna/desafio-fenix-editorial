import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MaterialModule } from '../../material.module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  imports: [MaterialModule, FormsModule],
  standalone: true,
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.scss'
})
export class SearchBarComponent implements OnInit {
  @Input() initialTerm: string = '';
  @Output() search = new EventEmitter<string>();

  searchTerm: string = '';

  ngOnInit() {
    if (this.initialTerm) {
      this.searchTerm = this.initialTerm;
    }
  }

  onSearch(event: any): void {
    this.search.emit(this.searchTerm);
  }
}
