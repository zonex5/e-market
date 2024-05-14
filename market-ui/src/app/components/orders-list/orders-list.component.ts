import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {DataViewModule} from "primeng/dataview";
import {ImageService} from "../../service/image.service";
import {CommonModule} from "@angular/common";
import {Apollo} from "apollo-angular";
import {QueryService} from "../../graphql/query.service";

@Component({
  selector: 'app-orders-list',
  standalone: true,
  imports: [
    TranslateModule,
    DataViewModule,
    CommonModule
  ],
  templateUrl: './orders-list.component.html',
  styleUrl: './orders-list.component.css'
})
export class OrdersListComponent implements OnInit {

  orders: any[] = []

  constructor(private route: ActivatedRoute,
              private apollo: Apollo,
              private qs: QueryService,
              private translateService: TranslateService,
              protected imageService: ImageService) {
  }

  statusClass(status: string) {
    switch (status) {
      case 'shipped':
        return {'status-new': true}
      case 'new' :
        return {'status-proposal': true}
      case 'completed' :
        return {'status-unknown': true}
      case 'canceled' :
        return {'status-unqualified': true}
      case 'pending' :
        return {'status-negotiation': true}
      default:
        return {}
    }
  }

  statusText(status: string) {
    if (status == 'new') {
      return 'Awaiting shipment';
    }
    return status;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const status = params['type']
      console.log(status)
      this.loadOrdersData(status)
    })
  }

  private loadOrdersData(status: string) {
    this.apollo.watchQuery<any>({
      query: this.qs.ordersData,
      variables: {status}
    }).valueChanges.subscribe({
      next: (result) => {
        this.orders = result.data?.getOrderData

        console.log(this.orders)
      },
      error: () => {
      }
    })
  }

}
