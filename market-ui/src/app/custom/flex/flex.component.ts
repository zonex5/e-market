import {Component, Input} from '@angular/core';

@Component({
  selector: 'flex',
  standalone: true,
  imports: [],
  templateUrl: './flex.component.html'
})
export class FlexComponent {
  @Input() class: string
}
