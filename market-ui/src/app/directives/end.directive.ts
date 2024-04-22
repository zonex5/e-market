import {Directive, ElementRef, Renderer2} from '@angular/core';

@Directive({
  selector: '[end]',
  standalone: true
})
export class EndDirective {

  constructor(private elementRef: ElementRef, private renderer: Renderer2) {
    this.renderer.setStyle(this.elementRef.nativeElement, 'justify-content', 'flex-end')
  }

}
