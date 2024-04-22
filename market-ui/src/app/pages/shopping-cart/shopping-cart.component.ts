import {Component, OnInit} from '@angular/core';
import {DataViewModule} from "primeng/dataview";
import {RatingModule} from "primeng/rating";
import {FormsModule} from "@angular/forms";
import {TagModule} from "primeng/tag";
import {ButtonModule} from "primeng/button";
import {CommonModule} from "@angular/common";
import {QuantityComponent} from "../../components/quantity/quantity.component";
import {TCartProductItem} from "../../types/TCartProductItem";
import {Apollo, QueryRef} from "apollo-angular";
import {QueryService} from "../../graphql/query.service";
import {environment} from "../../../environments/environment"
import {CardModule} from "primeng/card";
import {PanelModule} from "primeng/panel";
import {MutationDeleteProductFromCart, MutationUpdateQuantityInCart} from "../../graphql/mutation.service";
import {AvatarModule} from "primeng/avatar";
import {TooltipModule} from "primeng/tooltip";
import {MessagesModule} from "primeng/messages";
import {ConfirmationService, Message} from "primeng/api";
import {BlockUIModule} from "primeng/blockui";
import {ProductsSkeletonComponent} from "../../components/products-skeleton/products-skeleton.component";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import {ProgressBarModule} from "primeng/progressbar";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {RouterLink} from "@angular/router";
import {PaymentLogoComponent} from "../../components/payment-logo/payment-logo.component";

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  imports: [
    DataViewModule,
    RatingModule,
    FormsModule,
    TagModule,
    ButtonModule,
    CommonModule,
    QuantityComponent,
    CardModule,
    PanelModule,
    AvatarModule,
    TooltipModule,
    MessagesModule,
    BlockUIModule,
    ProductsSkeletonComponent,
    ProgressSpinnerModule,
    ProgressBarModule,
    ConfirmPopupModule,
    RouterLink,
    PaymentLogoComponent
  ],
  providers: [ConfirmationService],
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css'
})
export class ShoppingCartComponent implements OnInit {

  private readonly errorMsg = {severity: 'error', detail: 'An error has occurred, please try again!'}

  private queryCart: QueryRef<any>

  products: TCartProductItem[];
  totalAmount: number = 0
  savedAmount: number = 0

  loading: boolean = true
  fetching: boolean = false
  error: boolean = false

  messages: Message[] = []

  get hasItems() {
    return !!this.totalAmount && this.totalAmount > 0
  }

  imageUrl(imageId?: number | null): string {
    return imageId ? environment.imgDownloadUrl + imageId : '/assets/no-image.png'
  }

  hasDiscount(item: TCartProductItem) {
    return !!item?.discount && item.discount > 0
  }

  constructor(private apollo: Apollo,
              private qs: QueryService,
              private deleteProductFromCart: MutationDeleteProductFromCart,
              private updateQuantity: MutationUpdateQuantityInCart,
              private confirmationService: ConfirmationService
  ) {
    this.onQuantChange = this.onQuantChange.bind(this)
  }

  ngOnInit(): void {
    this.queryCart = this.apollo.watchQuery<any>({
      query: this.qs.queryCartProducts
    })
    this.queryCart.valueChanges.subscribe({
      next: (result) => {
        const data = result.data
        if (data?.customerCart) {
          this.products = data.customerCart.products || []
          this.totalAmount = data.customerCart.totalAmount
          this.savedAmount = data.customerCart.savedAmount
        }
        this.loading = result.loading
        this.fetching = result.loading
      },
      error: () => {
        this.loading = false
        this.fetching = false
        this.messages = [this.errorMsg]
      }
    })
  }

  deleteFromCart(id: number) {
    this.fetching = true
    this.deleteProductFromCart
      .mutate({id})
      .subscribe({
        complete: () => {
          this.queryCart.refetch()
        },
        error: () => {
          this.messages = [this.errorMsg]
          this.fetching = false
        }
      })
  }

  confirmDelete(event: any, id: number) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Are you sure you want to delete this product?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.deleteFromCart(id)
      }
    });
  }

  onQuantChange(quantity: number, id: number) {
    this.fetching = true
    this.updateQuantity
      .mutate({id, quantity})
      .subscribe({
        complete: () => {
          this.queryCart.refetch()
        },
        error: () => {
          this.messages = [this.errorMsg]
          this.loading = false
        }
      })
  }
}
