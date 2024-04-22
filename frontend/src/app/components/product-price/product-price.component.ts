import {Component, Input} from '@angular/core';
import {TProductPrice} from "../../types/TProductPrice";
import {CurrencyPipe, NgIf} from "@angular/common";
import {TagModule} from "primeng/tag";

@Component({
  selector: 'app-product-price',
  standalone: true,
  imports: [
    CurrencyPipe,
    NgIf,
    TagModule
  ],
  templateUrl: './product-price.component.html',
  styleUrl: './product-price.component.css'
})
export class ProductPriceComponent {

  @Input() price: TProductPrice | undefined | null
  @Input() inStock: boolean

  get available(): boolean {
    return !!this.price && this.inStock
  }

  get hasDiscount(): boolean {
    return this.discount > 0
  }

  get discount(): number {
    return this.price?.discount ?? 0
  }

  get hasSale(): boolean {
    return !!this.price && !!this.price.sale
  }

}
