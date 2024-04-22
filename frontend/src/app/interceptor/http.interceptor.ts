import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";

@Injectable()
export class HeaderHttpInterceptor implements HttpInterceptor {

  get token() {
    return localStorage.getItem('user.token')
  }

  get uuid() {
    return localStorage.getItem('user.uuid')
  }

  get language() {
    return localStorage.getItem('language') || 'us'
  }

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const langRequest = req.clone({
      headers: req.headers.set('language', this.language)
    })
    if (this.uuid && this.token) {
      const modifiedRequest = langRequest.clone({
        headers: langRequest.headers
          .set('customer', this.uuid)
          .set('Authorization', this.token)
      })
      return next.handle(modifiedRequest)
    } else {
      return next.handle(langRequest)
    }
  }
}
