import {Component, EventEmitter, inject, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {DropdownModule} from "primeng/dropdown";
import {NgForOf, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {ProductCardComponent} from "../../components/product-card/product-card.component";
import {ResultPage} from "../ResultPage";
import {MessagesModule} from "primeng/messages";
import {ProgressBarModule} from "primeng/progressbar";
import {SkeletonDataComponent} from "../../components/skeleton-data/skeleton-data.component";
import {Message} from "primeng/api";
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
    SkeletonDataComponent
  ],
  templateUrl: './favourite-products.component.html',
  styleUrl: './favourite-products.component.css'
})
export class FavouriteProductsComponent extends ResultPage implements OnInit, OnDestroy {

  private readonly addProductToWishList = inject(MutationProductToWishList)
  private queryFavourites: QueryRef<any>

  messages: Message[] = []

  ngOnInit(): void {
    this.loading = true
    this.queryFavourites = this.apollo.watchQuery({
      query: this.qs.queryFavouritesProducts
    })
    this.queryFavourites.valueChanges.subscribe(({data, loading, error}) => {
      this.products = (data as any).products
      this.loading = loading
      this.error = !!error
    })
  }

  refetch(): void {
  }

  remove(id: number) {
    this.addProductToWishList
      .mutate({id, flag: false})
      .subscribe({
        complete: () => {
          console.log('refetch')
          this.queryFavourites.refetch()
        },
        error: () => {
          this.messages = [{severity: 'error', detail: 'Please try again.'}]
        }
      })
  }

}
