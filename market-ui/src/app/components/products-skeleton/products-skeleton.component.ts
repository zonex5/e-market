import {Component} from '@angular/core';
import {SkeletonModule} from "primeng/skeleton";
import {CommonModule} from "@angular/common";
import {ProgressBarModule} from "primeng/progressbar";

@Component({
  selector: 'app-products-skeleton',
  standalone: true,
  imports: [
    SkeletonModule,
    CommonModule,
    ProgressBarModule
  ],
  templateUrl: './products-skeleton.component.html'
})
export class ProductsSkeletonComponent {
  counterArray = Array(10)
}
