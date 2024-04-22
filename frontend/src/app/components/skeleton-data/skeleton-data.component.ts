import {Component} from '@angular/core';
import {SkeletonModule} from "primeng/skeleton";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-skeleton-data',
  standalone: true,
  imports: [
    SkeletonModule,
    CommonModule
  ],
  templateUrl: './skeleton-data.component.html',
  styleUrl: './skeleton-data.component.css'
})
export class SkeletonDataComponent {
  counterArray = Array(10)
}
