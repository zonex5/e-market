import {AfterViewInit, Component, ElementRef, Input, OnInit, Renderer2, ViewChild} from '@angular/core';

@Component({
  selector: 'flex-end',
  standalone: true,
  imports: [],
  templateUrl: './flex-end.component.html'
})
export class FlexEndComponent implements AfterViewInit {

  @Input() class: string
  @ViewChild('div') div: ElementRef

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  ngAfterViewInit() {
    if (this.class) {
      this.class.split(' ').forEach(cls => {
        this.renderer.addClass(this.div.nativeElement, cls)
        this.renderer.removeAttribute(this.el.nativeElement, 'class')

        console.log(this.el.nativeElement)
      })
    }
  }
}
