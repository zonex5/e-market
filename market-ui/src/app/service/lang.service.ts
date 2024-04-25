import {Injectable} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Injectable({
  providedIn: 'root'
})
export class LangService {

  constructor(public translate: TranslateService) { }

  get language() {
    return localStorage.getItem('language') || 'us'
  }

  setLanguage(lang: string) {
    localStorage.setItem('language', lang)
    this.translate.use(lang);
  }
}
