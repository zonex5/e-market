import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TProductData} from "../../types/TProductData";
import {environment} from '../../../environments/environment';
import {CurrencyPipe, NgIf, NgOptimizedImage} from "@angular/common";
import {CardModule} from "primeng/card";
import {ButtonModule} from "primeng/button";
import {ImageModule} from "primeng/image";
import {TagModule} from "primeng/tag";
import {RouterLink} from "@angular/router";
import {ProductPriceComponent} from "../product-price/product-price.component";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {ConfirmationService} from "primeng/api";

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [
    NgOptimizedImage,
    CardModule,
    ButtonModule,
    ImageModule,
    TagModule,
    NgIf,
    CurrencyPipe,
    RouterLink,
    ProductPriceComponent,
    ConfirmPopupModule
  ],
  providers: [ConfirmationService],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.css'
})
export class ProductCardComponent {
  @Input() data: TProductData
  @Input() showRemoveButton: boolean = false

  @Output() removeEvent = new EventEmitter<number>();

  constructor(private confirmationService: ConfirmationService) {
  }

  get imageUrl(): string {
    return this.data.thumbnail ? environment.imgDownloadUrl + this.data.thumbnail : '/assets/no-image.png'
  }

  remove(event: any) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Are you sure you want to delete this product from favourites?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.removeEvent.emit(this.data.id)
      }
    })
  }

}
