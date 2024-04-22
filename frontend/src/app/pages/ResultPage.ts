import {TSortingConfig} from "../types/TSortingConfig";
import {TSortingMode} from "../types/TSortingMode";
import {TProductData} from "../types/TProductData";
import {Subscription} from "rxjs";
import {Component, OnDestroy} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {QueryService} from "../graphql/query.service";
import {Apollo} from "apollo-angular";

@Component({template: ''})
export abstract class ResultPage implements OnDestroy {

  constructor(public route: ActivatedRoute, public qs: QueryService, public apollo: Apollo) {
  }

  private _loading: boolean = false  //todo правильно высчитывать
  private _error: boolean = false

  products: TProductData[] = []
  totalProducts: number = 0

  routeSubscription: Subscription | undefined

  protected firstRecords: number = 0

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

  set loading(value: boolean) {
    this._loading = value;
  }

  get loading(): boolean {
    return this._loading;
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
