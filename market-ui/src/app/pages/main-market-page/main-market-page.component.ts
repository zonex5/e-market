import {Component, OnInit} from '@angular/core';
import {RatingModule} from "primeng/rating";
import {ButtonModule} from "primeng/button";
import {ResultPage} from "../ResultPage";
import {DropdownModule} from "primeng/dropdown";
import {NgForOf, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {ProductCardComponent} from "../../components/product-card/product-card.component";
import {ProductsSkeletonComponent} from "../../components/products-skeleton/products-skeleton.component";
import {MessagesModule} from "primeng/messages";
import {TranslateModule} from "@ngx-translate/core";

@Component({
  selector: 'app-main-market-page',
  templateUrl: './main-market-page.component.html',
  standalone: true,
  imports: [
    RatingModule,
    ButtonModule,
    DropdownModule,
    NgForOf,
    NgIf,
    PaginatorModule,
    ProductCardComponent,
    ProductsSkeletonComponent,
    MessagesModule,
    TranslateModule
  ],
  styleUrls: ['./main-market-page.component.css']
})
export class MainMarketPageComponent extends ResultPage implements OnInit {

  ngOnInit(): void {
    this.loadCategoryProducts()
  }

  refetch(): void {
  }

  loadCategoryProducts(): void {
    this.loadingData = true

    this.apollo.watchQuery({
      query: this.qs.queryProductsMainPage,
      variables: {
        sort: {...this.currentSort, size: 50}
      }
    }).valueChanges.subscribe(this.productsSubscriptionHandlers)
  }

}
