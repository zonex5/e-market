import {Component, OnInit} from '@angular/core';
import {IconFieldModule} from "primeng/iconfield";
import {InputIconModule} from "primeng/inputicon";
import {InputTextModule} from "primeng/inputtext";
import {NgForOf, NgIf} from "@angular/common";
import {ProductCardComponent} from "../../components/product-card/product-card.component";
import {QueryService} from "../../graphql/query.service";
import {Apollo, QueryRef} from "apollo-angular";
import {ActivatedRoute} from "@angular/router";
import {TProductData} from "../../types/TProductData";
import {FormsModule} from "@angular/forms";
import {PaginatorModule} from "primeng/paginator";
import {TSortingConfig} from "../../types/TSortingConfig";
import {ResultPage} from "../ResultPage";

@Component({
  selector: 'app-products-search-market-page',
  templateUrl: './products-search-market-page.component.html',
  standalone: true,
  imports: [
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    NgForOf,
    ProductCardComponent,
    FormsModule,
    NgIf,
    PaginatorModule
  ],
  styleUrls: ['./products-search-market-page.component.css']
})
export class ProductsSearchMarketPageComponent extends ResultPage implements OnInit {

  randomProducts: TProductData[] = []

  searchText: string = ''
  searchValue: string = ''

  private productsQuery: QueryRef<unknown, { text: string; sort: TSortingConfig; }>

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const text = params['text']
      if (text && text.length > 0) {
        this.loadProductsByText(text)
      }
      this.loadRandomProducts()
    })
  }

  loadRandomProducts(): void {
    this.apollo.watchQuery({
      query: this.qs.queryProductsRandom,
      fetchPolicy: "network-only"
    }).valueChanges.subscribe(this.productsSubscriptionHandlers)
  }

  loadProductsByText(text: string) {
    this.searchText = text
    this.searchValue = text

    //load total products count
    this.apollo.watchQuery({
      query: this.qs.totalProductsText,
      variables: {text}
    }).valueChanges.subscribe(this.totalProductsSubscriptionHandlers)

    //load products
    this.productsQuery = this.apollo.watchQuery({
      query: this.qs.queryProductsByText,
      variables: {text, sort: this.currentSort}
    })
    this.productsQuery.valueChanges.subscribe(this.productsSubscriptionHandlers)
  }

  onSearchButtonClick() {
    this.searchValue = this.searchText
    if (this.searchText && this.searchText.length > 1) {
      this.loadProductsByText(this.searchText)
      this.loadRandomProducts()
    }
    if (this.searchText && this.searchText.length == 1) {
      this.products = []
    }
  }

  onKeyDown = (event: any) => {
    if (event.key === 'Enter') {
      this.onSearchButtonClick()
    }
  }

  refetch() {
    this.productsQuery.refetch({
      text: this.searchText,
      sort: this.currentSort
    }).then()
  }
}
