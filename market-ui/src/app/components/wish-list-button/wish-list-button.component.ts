import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CommonModule} from "@angular/common";
import {TooltipModule} from "primeng/tooltip";
import {AuthService} from "../../service/auth.service";
import {TranslateModule} from "@ngx-translate/core";

@Component({
  selector: 'app-wish-list-button',
  standalone: true,
  imports: [
    CommonModule,
    TooltipModule,
    TranslateModule
  ],
  templateUrl: './wish-list-button.component.html',
  styleUrl: './wish-list-button.component.css'
})
export class WishListButtonComponent {

  @Input() checked: boolean = false

  @Output() onClick: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(private authService: AuthService) {}

  click() {
    if (this.authService.authenticated) {
      this.checked = !this.checked
    }
    if (this.onClick) this.onClick.emit(this.checked)
  }

}
