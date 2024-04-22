import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout'
import {GalleriaModule} from "primeng/galleria";
import {QueryService} from "../../graphql/query.service";
import {Apollo} from "apollo-angular";
import {TProductData} from "../../types/TProductData";
import {environment} from "../../../environments/environment";
import {WishListButtonComponent} from "../../components/wish-list-button/wish-list-button.component";
import {ProductPriceComponent} from "../../components/product-price/product-price.component";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";
import {InputNumberModule} from "primeng/inputnumber";
import {ButtonModule} from "primeng/button";
import {FlexComponent} from "../../custom/flex/flex.component";
import {EndDirective} from "../../directives/end.directive";
import {FlexEndComponent} from "../../custom/flex-end/flex-end.component";
import {FlexStartComponent} from "../../custom/flex-start/flex-start.component";
import {TProductPrice} from "../../types/TProductPrice";
import {TagModule} from "primeng/tag";
import {CartService} from "../../service/cart.service";
import {TCartNewItem} from "../../types/TCartNewItem";
import {AuthService} from "../../service/auth.service";
import {MutationProductToWishList} from "../../graphql/mutation.service";
import {ChipModule} from "primeng/chip";
import {QuantityComponent} from "../../components/quantity/quantity.component";

class SimpleVariant {
  price: TProductPrice | undefined | null
  productId: number
  inStock: boolean
  sku: string

  constructor(productId: number, sku: string, price?: TProductPrice, inStock?: boolean) {
    this.productId = productId
    this.price = price
    this.inStock = inStock || false
    this.sku = sku
  }

  /*static fromVariant(variant: TProductVariant): SimpleVariant {
    return new SimpleVariant(variant.productId, variant.price, variant.inStock, variant.sku);
  }*/

  static fromTProductData(product: TProductData): SimpleVariant {
    return new SimpleVariant(product.id, product.sku, product.price, product.available)
  }
}

@Component({
  selector: 'app-product-details-market-page',
  templateUrl: './product-details-market-page.component.html',
  standalone: true,
  imports: [
    GalleriaModule,
    WishListButtonComponent,
    ProductPriceComponent,
    DropdownModule,
    FormsModule,
    InputNumberModule,
    ButtonModule,
    FlexComponent,
    EndDirective,
    FlexEndComponent,
    FlexStartComponent,
    TagModule,
    ChipModule,
    QuantityComponent
  ],
  styleUrls: ['./product-details-market-page.component.css']
})
export class ProductDetailsMarketPageComponent implements OnInit { //todo unsubscribe

  private id: number //from params

  product: TProductData
  loading: boolean
  error: boolean
  inStock: boolean
  inCart: boolean
  disabledButton: boolean = true

  smallScreen: boolean = true

  userSelection: any = {}
  productCount: number = 1

  selectedVariant: SimpleVariant

  get variantSelected(): boolean {
    return Object.values(this.userSelection).every(value => value != null)
  }

  get selectedVariantPrice() {
    return this.selectedVariant?.price
  }

  get imageUrls(): string[] {
    return this.product?.images?.map(id => environment.imgDownloadUrl + id)
  }

  constructor(private route: ActivatedRoute,
              private qs: QueryService,
              private apollo: Apollo,
              private cartService: CartService,
              private breakpointObserver: BreakpointObserver,
              private addProductToWishList: MutationProductToWishList,
              protected authService: AuthService) {
  }

  ngOnInit(): void {
    this.breakpointObserver.observe([
      Breakpoints.XSmall
    ]).subscribe(result => {
      this.smallScreen = result.breakpoints[Breakpoints.XSmall]
    })

    /*this.selectedVariant$.subscribe(v => {
      if (v.productId) {
        if (this.cartSubscription) {
          this.cartSubscription.unsubscribe()
          this.cartSubscription = null
        }
        this.cartSubscription = this.cartService.inCart(v.productId).subscribe(v => this.inCart = v)
      }
    })*/


    // load product details
    this.route.params.subscribe(params => {
      this.id = params['id']
      if (this.id) {
        this.loadDetails()
      }
    })
  }

  private loadDetails() {
    this.apollo.watchQuery({
      query: this.qs.queryProductDetails,
      variables: {id: this.id}
    }).valueChanges.subscribe(({data, error, loading}) => {
      this.product = (data as any).product
      this.inStock = this.product.available
      this.loading = loading
      this.error = !!error

      //this is simple product without variants
      if (this.product && this.product.attributes.length == 0) {
        //create default variant from product
        this.selectedVariant = SimpleVariant.fromTProductData(this.product)
        this.disabledButton = !this.product.available
      }
      if (this.product && this.product.attributes) {
        //create initial selection
        this.product?.attributes?.forEach(attr => this.userSelection[attr.name] = null)
      }

      //subscribe to shopping cart
      this.cartService.cartItems$.subscribe(v => {
        this.setInCartValue(v)
      })
    })
  }

  private setInCartValue(cartItems: TProductData[]) {
    this.inCart = !!this.selectedVariant
      && !!this.selectedVariant.productId
      && cartItems.some((item: TProductData) => item.id == this.selectedVariant.productId)
  }

  onAttributeChange() {
    const variant = this.findVariant()
    this.inStock = !!variant?.inStock
    this.disabledButton = !this.inStock || !variant?.price
    this.inCart = false
    if (variant) {
      this.selectedVariant = variant
      this.setInCartValue(this.cartService.cartItems$.getValue())
    }
  }

  onBtnAddClick() {

    //check if user is signed in
    if (!this.authService.authenticated) {
      this.authService.showLoginDialog()
      return
    }

    if (this.selectedVariant && this.selectedVariant.productId && this.productCount > 0) {
      const item: TCartNewItem = {productId: this.selectedVariant.productId, quantity: this.productCount}
      this.cartService.addToCart(item)
    }
  }

  onWishListButtonClick(checked: boolean) {

    //check if user is signed in
    if (!this.authService.authenticated) {
      this.authService.showLoginDialog()
      return
    }

    //call API with changes
    this.addProductToWishList
      .mutate({id: this.product.id, flag: checked})
      .subscribe()
  }

  findVariant(): SimpleVariant {
    const objectsEqual = (obj1: any, obj2: any) => {  //todo extract to service
      if (!(obj1 && obj2)) return false;
      let equals = true
      for (const key of Object.keys(obj1)) {
        if (obj1[key] !== obj2[key]) {
          equals = false;
        }
      }
      return equals
    }

    let variant: any | null = null  //todo add type
    this.product.variants.forEach(v => {
      const attrs: any = {}
      v.attributes.forEach(a => {
        attrs[a.name] = a.value
      })
      if (objectsEqual(this.userSelection, attrs)) {
        variant = v
      }
    })
    console.log(variant)
    return variant
  }
}
