import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QueryService} from "../../graphql/query.service";
import {Apollo, QueryRef} from "apollo-angular";
import {ProductCardComponent} from "../../components/product-card/product-card.component";
import {NgForOf, NgIf} from "@angular/common";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";
import {TSortingConfig} from "../../types/TSortingConfig";
import {PaginatorModule} from "primeng/paginator";
import {ResultPage} from "../ResultPage";
import {MessagesModule} from "primeng/messages";
import {ProgressBarModule} from "primeng/progressbar";
import {SkeletonDataComponent} from "../../components/skeleton-data/skeleton-data.component";
import {Message} from "primeng/api";

@Component({
  selector: 'app-products-market-page',
  templateUrl: './products-market-page.component.html',
  standalone: true,
    imports: [
        ProductCardComponent,
        NgForOf,
        DropdownModule,
        FormsModule,
        PaginatorModule,
        NgIf,
        MessagesModule,
        ProgressBarModule,
        SkeletonDataComponent
    ],
  styleUrls: ['./products-market-page.component.css']
})
export class ProductsMarketPageComponent extends ResultPage implements OnInit, OnDestroy {

  private categoryId: number

  private productsQuery: QueryRef<unknown, { id: number; sort: TSortingConfig; }>

  messages: Message[] = []

  loadCategoryProducts(id: number): void {
    this.categoryId = id

    //load total products count
    this.apollo.watchQuery({
      query: this.qs.totalProductsCategory,
      variables: {id: id,}
    }).valueChanges.subscribe(({data, loading, error}) => {
      this.totalProducts = (data as any).totalProducts
      this.loading ||= loading //todo
      this.error ||= !!error
    })

    // load products
    this.productsQuery = this.apollo.watchQuery({
      query: this.qs.queryProductsByCategory,
      variables: {
        id: id,
        sort: this.currentSort
      }
    })
    this.productsQuery.valueChanges.subscribe(({data, loading, error}) => {
      this.products = (data as any).products
      this.loading ||= loading //todo
      this.error ||= !!error
    })
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.categoryId = params['id']
      this.categoryId && this.loadCategoryProducts(this.categoryId)
    })
  }

  refetch() {
    this.productsQuery.refetch({
      id: this.categoryId,
      sort: this.currentSort
    }).then()
  }
}
