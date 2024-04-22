import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteProductsComponent } from './favourite-products.component';

describe('FavouriteProductsComponent', () => {
  let component: FavouriteProductsComponent;
  let fixture: ComponentFixture<FavouriteProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FavouriteProductsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FavouriteProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
