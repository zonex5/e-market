import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {DropdownModule} from "primeng/dropdown";
import {NgForOf, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {ProductCardComponent} from "../../components/product-card/product-card.component";
import {ResultPage} from "../ResultPage";
import {MessagesModule} from "primeng/messages";
import {ProgressBarModule} from "primeng/progressbar";
import {ProductsSkeletonComponent} from "../../components/products-skeleton/products-skeleton.component";
import {MutationProductToWishList} from "../../graphql/mutation.service";
import {QueryRef} from "apollo-angular";

@Component({
  selector: 'app-favourite-products',
  standalone: true,
  imports: [
    DropdownModule,
    NgForOf,
    NgIf,
    PaginatorModule,
    ProductCardComponent,
    MessagesModule,
    ProgressBarModule,
    ProductsSkeletonComponent
  ],
  templateUrl: './favourite-products.component.html',
  styleUrl: './favourite-products.component.css'
})
export class FavouriteProductsComponent extends ResultPage implements OnInit, OnDestroy {

  private readonly addProductToWishList = inject(MutationProductToWishList)
  private queryFavourites: QueryRef<any>

  ngOnInit(): void {
    this.loadingData = true
    this.queryFavourites = this.apollo.watchQuery({
      query: this.qs.queryFavouritesProducts
    })
    this.queryFavourites.valueChanges.subscribe(this.productsSubscriptionHandlers)
  }

  refetch(): void {
  }

  remove(id: number) {
    this.addProductToWishList
      .mutate({id, flag: false})
      .subscribe({
        complete: () => {
          this.queryFavourites.refetch()
        },
        error: () => {
          this.messages = [{severity: 'error', detail: 'Please try again.'}]
        }
      })
  }
}
