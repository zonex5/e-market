import {TSortingConfig} from "../types/TSortingConfig";
import {TSortingMode} from "../types/TSortingMode";
import {TProductData} from "../types/TProductData";
import {Subscription} from "rxjs";
import {Component, OnDestroy} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {QueryService} from "../graphql/query.service";
import {Apollo} from "apollo-angular";
import {Message} from "primeng/api";

@Component({template: ''})
export abstract class ResultPage implements OnDestroy {

  constructor(public route: ActivatedRoute, public qs: QueryService, public apollo: Apollo) {
  }

  private _error: boolean = false

  loadingTotals: boolean = false
  loadingData: boolean = false

  products: TProductData[] = []
  totalProducts: number = 0

  routeSubscription: Subscription | undefined

  protected firstRecords: number = 0

  messages: Message[] = []
  readonly errorMsg = {severity: 'error', detail: 'An error has occurred, please try again!'}

  sortModeList: TSortingMode[] = [
    {label: 'Price, low to high', value: 'cost', direction: 'ASC'},
    {label: 'Price, high to low', value: 'cost', direction: 'DESC'},
    {label: 'Newest first', value: 'date', direction: 'ASC'}
  ]

  sortMode: TSortingMode = this.sortModeList[2]

  initialSort: TSortingConfig = {
    field: this.sortMode.value,
    direction: this.sortMode.direction,
    page: 0,
    size: 5
  }

  currentSort: TSortingConfig = {...this.initialSort}

  get loading(): boolean {
    return this.loadingTotals || this.loadingData;
  }

  set error(value: boolean) {
    this._error = value;
  }

  get error(): boolean {
    return this._error;
  }

  get hasResults(): boolean {
    return this.products && this.products.length > 0
  }

  get showPaginator(): boolean {
    return this.totalProducts > this.initialSort.size
  }

  setLoading(value: boolean) {
    this.loadingTotals = value
    this.loadingData = value
  }

  onError = () => {
    //on error set loading to 'false' for all events
    this.setLoading(false)
    this.messages = [this.errorMsg]
  }

  productsSubscriptionHandlers = {
    next: (result: any) => {
      this.products = result?.data?.products;
      this.loadingData = false;
    },
    error: this.onError
  }

  totalProductsSubscriptionHandlers = {
    next: (result: any) => {
      this.totalProducts = result?.data?.totalProducts
      this.loadingData = false;
    },
    error: this.onError
  }

  ngOnDestroy(): void {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe()
    }
  }

  onPageChange(v: any) {
    console.log(v)
    this.firstRecords = v.first
    this.currentSort = {
      ...this.currentSort,
      page: v.page
    }
    this.refetch()
  }

  onSortChange() {
    this.firstRecords = 0
    this.currentSort = {
      field: this.sortMode.value,
      direction: this.sortMode.direction,
      page: this.initialSort.page,
      size: this.initialSort.size
    }
    this.refetch()
  }

  abstract refetch(): void
}
