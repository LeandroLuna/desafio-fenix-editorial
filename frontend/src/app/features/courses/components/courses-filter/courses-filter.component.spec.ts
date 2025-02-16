import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CoursesFilterComponent } from './courses-filter.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../../../../shared/material.module';
import { By } from '@angular/platform-browser';

describe('CoursesFilterComponent', () => {
  let component: CoursesFilterComponent;
  let fixture: ComponentFixture<CoursesFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CoursesFilterComponent],
      imports: [
        NoopAnimationsModule,
        MaterialModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CoursesFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should emit category when selection changes', () => {
    const spy = spyOn(component.categoryChange, 'emit');
    component.onCategoryChange('Programação');
    expect(spy).toHaveBeenCalledWith('Programação');
  });

  it('should emit sort order when selection changes', () => {
    const spy = spyOn(component.sortChange, 'emit');
    component.onSortChange('desc');
    expect(spy).toHaveBeenCalledWith('desc');
  });

  it('should display all available categories', () => {
    fixture.detectChanges();
    const categories = component.categories;
    expect(categories.length).toBeGreaterThan(0);
    expect(categories).toContain('Todos');
  });

  it('should display sort options', () => {
    fixture.detectChanges();
    const select = fixture.debugElement.query(By.css('mat-select'));
    expect(select).toBeTruthy();

    const options = component.sortOptions;
    expect(options.length).toBe(2);
    expect(options[0].label).toContain('Duração: Menor para Maior');
    expect(options[1].label).toContain('Duração: Maior para Menor');
  });
});
