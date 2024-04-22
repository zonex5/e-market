import {Component, forwardRef, Input, OnInit} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {ToStringPipe} from "../../pipes/to-string.pipe";
import {NG_VALUE_ACCESSOR} from "@angular/forms";

@Component({
  selector: 'app-quantity',
  standalone: true,
  imports: [
    ButtonModule,
    ToStringPipe
  ],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => QuantityComponent),
    multi: true
  }],
  templateUrl: './quantity.component.html',
  styleUrl: './quantity.component.css'
})
export class QuantityComponent implements OnInit {

  @Input() value: number
  @Input() productId: number
  @Input() onChange: Function

  private min: number = 1
  private max: number = 99

  get btnStyle() {
    return {width: '1.5rem', height: '1.5rem', 'border-radius': '100%', padding: '0.7rem 0.7rem', border: 0, background: '#f3f4f6', color: '#4b5563'}
  }

  get btnMinDisabled() {
    return this.value == this.min
  }

  get btnMaxDisabled() {
    return this.value == this.max
  }

  ngOnInit(): void { }

  inc() {
    if (this.value + 1 <= this.max) {
      this.value++
      this.onChange(this.value, this.productId)
    }
  }

  dec() {
    if (this.value - 1 >= this.min) {
      this.value--
      this.onChange(this.value, this.productId)
    }
  }
}
