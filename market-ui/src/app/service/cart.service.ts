import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Apollo, QueryRef} from "apollo-angular";
import {QueryService} from "../graphql/query.service";
import {TCartNewItem} from "../types/TCartNewItem";
import {MutationAddProductToCart} from "../graphql/mutation.service";
import {TProductData} from "../types/TProductData";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private readonly queryTotal: QueryRef<any>

  public cartItems$ = new BehaviorSubject<TProductData[]>([])

  get hasItems(): boolean {
    return this.cartItems$.getValue().length > 0
  }

  constructor(private apollo: Apollo, private qs: QueryService, private mutationAddToCart: MutationAddProductToCart) {
    this.queryTotal = this.apollo.watchQuery<any>({
      query: this.qs.queryCartCount
    });

    this.queryTotal.valueChanges.subscribe(({data, error, loading}) => {
      this.cartItems$.next(data?.customerCart?.products || [])
    })
  }

  addToCart(item: TCartNewItem) {
    this.mutationAddToCart
      .mutate({id: item.productId, quantity: item.quantity})
      .subscribe({
          complete: () => {
            this.queryTotal.refetch().then()
          }
        }
      )
  }

  refresh() {
    this.queryTotal.refetch().then()
  }
}
