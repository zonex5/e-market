import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LangService {

  get language() {
    return localStorage.getItem('language') || 'us'
  }

  set language(lang: string) {
    localStorage.setItem('language', lang)
  }

  constructor() { }

}
