import {Component} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {environment} from '../../../environments/environment';
import {RouterLink} from "@angular/router";
import {TranslateModule} from "@ngx-translate/core";

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [
    ButtonModule,
    RouterLink,
    TranslateModule
  ],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {

  openUrl(url: string) {
    window.open(url, '_blank')
  }

  protected readonly environment = environment;
}
