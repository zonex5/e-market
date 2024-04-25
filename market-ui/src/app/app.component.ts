import {Component} from '@angular/core'
import {RouterOutlet} from '@angular/router'
import {ApplayoutComponent} from "./components/applayout/applayout.component"
import {TranslateModule, TranslateService} from "@ngx-translate/core"
import {LangService} from "./service/lang.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ApplayoutComponent, TranslateModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'EMarket'

  constructor(translate: TranslateService, langService: LangService) {
    translate.setDefaultLang('us')
    translate.use(langService.language)
  }
}
