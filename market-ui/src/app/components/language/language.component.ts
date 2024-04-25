import {Component, OnInit} from '@angular/core';
import {MenuModule} from "primeng/menu";
import {BadgeModule} from "primeng/badge";
import {CommonModule} from "@angular/common";
import {Apollo} from "apollo-angular";
import {MenuItem} from "primeng/api";
import {QueryService} from "../../graphql/query.service";
import {AvatarModule} from "primeng/avatar";
import {TLanguageItem} from '../../types/TLanguageItem';
import {LangService} from "../../service/lang.service";

@Component({
  selector: 'app-language',
  standalone: true,
  imports: [
    MenuModule,
    BadgeModule,
    CommonModule,
    AvatarModule
  ],
  templateUrl: './language.component.html',
  styleUrl: './language.component.css'
})
export class LanguageComponent implements OnInit {

  menuItems: MenuItem[] = []

  selectedLanguage: TLanguageItem

  constructor(private qs: QueryService, private apollo: Apollo, private langService: LangService) {
  }

  ngOnInit(): void {
    this.selectedLanguage = {code: this.langService.language}

    this.apollo.watchQuery({
      query: this.qs.allLanguages
    })
      .valueChanges.subscribe(({data}) => {
      (data as any).languages.forEach((lang: any) => {
        this.menuItems.push({
          label: lang.name,
          icon: 'fi fi-' + lang.code.toLowerCase(),
          command: () => this.changeLanguage(lang)
        })
      })
    })
  }

  changeLanguage(lang: TLanguageItem) {
    this.selectedLanguage = lang
    this.langService.setLanguage(lang.code)
    location.reload()
  }
}
