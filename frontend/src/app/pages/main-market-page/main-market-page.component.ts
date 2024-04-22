import {Component, OnInit} from '@angular/core';
import {RatingModule} from "primeng/rating";
import {ButtonModule} from "primeng/button";
import {ResultPage} from "../ResultPage";
import {DropdownModule} from "primeng/dropdown";
import {NgForOf, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {ProductCardComponent} from "../../components/product-card/product-card.component";

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
    ProductCardComponent
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
    this.apollo.watchQuery({
      query: this.qs.queryProductsMainPage,
      variables: {
        sort: {...this.currentSort, size: 50}
      }
    }).valueChanges.subscribe(({data, loading, error}) => {
      this.products = (data as any).products
      this.loading ||= loading
      this.error ||= !!error
    })
  }

}
