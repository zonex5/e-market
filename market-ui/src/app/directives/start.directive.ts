import {Directive, ElementRef, Renderer2} from '@angular/core';

@Directive({
  selector: '[start]',
  standalone: true
})
export class StartDirective {

  constructor(private elementRef: ElementRef, private renderer: Renderer2) {
    this.renderer.setStyle(this.elementRef.nativeElement, 'justify-content', 'flex-start')
  }

}
