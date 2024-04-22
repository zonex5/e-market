import {Directive, ElementRef, Renderer2} from '@angular/core';

@Directive({
  selector: '[between]',
  standalone: true
})
export class BetweenDirective {

  constructor(private elementRef: ElementRef, private renderer: Renderer2) {
    this.renderer.setStyle(this.elementRef.nativeElement, 'justify-content', 'space-between')
  }

}
