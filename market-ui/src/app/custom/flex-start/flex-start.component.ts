import {Component, Input} from '@angular/core';

@Component({
  selector: 'flex-start',
  standalone: true,
  imports: [],
  templateUrl: './flex-start.component.html'
})
export class FlexStartComponent {
  @Input() class: string
}
