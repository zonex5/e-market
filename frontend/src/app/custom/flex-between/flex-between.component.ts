import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-flex-between',
  standalone: true,
  imports: [],
  templateUrl: './flex-between.component.html',
  styleUrl: './flex-between.component.css'
})
export class FlexBetweenComponent {
  @Input() class: string
}
