import {Component, OnInit} from '@angular/core';
import {InputTextModule} from "primeng/inputtext";
import {DropdownModule} from "primeng/dropdown";
import {CheckboxModule} from "primeng/checkbox";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {CurrencyPipe, NgIf} from "@angular/common";
import {PanelModule} from "primeng/panel";
import {PaymentLogoComponent} from "../../components/payment-logo/payment-logo.component";
import {ButtonModule} from "primeng/button";
import {QueryService} from "../../graphql/query.service";
import {Apollo} from "apollo-angular";
import {TCountry} from "../../types/TCountry";
import {TooltipModule} from "primeng/tooltip";
import {MutationFinishOrder} from "../../graphql/mutation.service";
import {TranslateModule} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {CartService} from "../../service/cart.service";

interface DeliveryData {
  controls: {
    [key: string]: any
  }
}

type AmountsData = {
  subtotal: number
  shipping: number
  total: number
}

type ShippingMethod = {
  methodName: string,
  cost: number
}

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [
    InputTextModule,
    DropdownModule,
    CheckboxModule,
    ReactiveFormsModule,
    CurrencyPipe,
    PanelModule,
    PaymentLogoComponent,
    ButtonModule,
    NgIf,
    TooltipModule,
    TranslateModule
  ],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit {

  deliveryData = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phone: ['', [Validators.required, Validators.pattern(/^\+?[0-9]{1,20}$/)]],
    country: ['', Validators.required],
    address: ['', Validators.required],
    zip: ['', Validators.required],
    city: ['', Validators.required],
    save: [false]
  })

  countries: TCountry[]

  amounts: AmountsData = {
    subtotal: 0,
    shipping: 0,
    total: 0
  }

  shipping: ShippingMethod = {
    methodName: '',
    cost: 0
  }

  constructor(private qs: QueryService,
              private apollo: Apollo,
              private fb: FormBuilder,
              private router: Router,
              private cartService: CartService,
              private mutationFinishOrder: MutationFinishOrder
  ) {}

  ngOnInit(): void {

    //get countries
    this.apollo.watchQuery({
      query: this.qs.allCountries
    })
      .valueChanges.subscribe(({data}) => {
      this.countries = (data as any).countries
    })

    //get saved data
    this.apollo.watchQuery({
      query: this.qs.newOrderData
    })
      .valueChanges.subscribe(({data}) => {
      const orderData = (data as any).newOrderData
      this.setFormData(orderData?.customerData)
      this.amounts = orderData?.amounts
      this.shipping = orderData?.shippingMethod
    })
  }

  setFormData(data: any) {
    this.deliveryData.patchValue({
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      phone: data.phone,
      country: data.countryId,
      address: data.address,
      zip: data.zip,
      city: data.city,
      save: data.save
    })
  }

  submit() {
    Object.keys(this.deliveryData.controls).forEach(field => {
      const control = this.deliveryData.get(field);
      control?.markAsTouched({onlySelf: true});
      control?.markAsDirty()
    })

    //finish order
    if (this.deliveryData.valid) {
      this.mutationFinishOrder
        .mutate({data: this.deliveryData.value})
        .subscribe({
          next: ({data}) => {
            this.cartService.refresh()
            this.router.navigate(['/orders/list', 'new']);
          },
          complete: () => {
            console.log('complete')
          },
          error: () => {
            console.log('error')
          }
        })
    }
  }

  invalid(field: string) {
    const data: DeliveryData = this.deliveryData
    const control = data.controls[field]
    return control && control.invalid && (control.dirty || control.touched)
  }

}
