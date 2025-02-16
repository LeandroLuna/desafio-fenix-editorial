import { ComponentFixture, TestBed, fakeAsync, tick, discardPeriodicTasks } from '@angular/core/testing';
import { SearchBarComponent } from './search-bar.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../../material.module';

describe('SearchBarComponent', () => {
  let component: SearchBarComponent;
  let fixture: ComponentFixture<SearchBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        SearchBarComponent,
        NoopAnimationsModule,
        MaterialModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(SearchBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should emit search term when input changes', () => {
    const searchTerm = 'Angular';
    const spy = spyOn(component.search, 'emit');

    const input = fixture.nativeElement.querySelector('input');
    input.value = searchTerm;
    input.dispatchEvent(new Event('input'));

    expect(spy).toHaveBeenCalledWith(searchTerm);
  });

  it('should debounce search term emission', fakeAsync(() => {
    const spy = spyOn(component.search, 'emit');
    
    component.onSearch('a');
    tick(100);
    
    component.onSearch('an');
    tick(100);
    
    component.onSearch('ang');
    tick(100);
    
    expect(spy).toHaveBeenCalledWith('ang');
    
    discardPeriodicTasks();
  }));
});
