import {ApplicationConfig, importProvidersFrom, Provider} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient} from '@angular/common/http';
import {graphqlProvider} from './graphql.provider';
import {FormsModule} from '@angular/forms';
import {provideAnimations} from '@angular/platform-browser/animations';
import {HeaderHttpInterceptor} from "./interceptor/http.interceptor";
import {provideStore} from '@ngrx/store';
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";


const httpInterceptorProvider: Provider = {provide: HTTP_INTERCEPTORS, useClass: HeaderHttpInterceptor, multi: true};

export function HttpLoaderFactory(httpClient: HttpClient) {
  return  new  TranslateHttpLoader(httpClient, './assets/i18n/', '.json');
}

export const provideTranslation = () => ({
  defaultLanguage: 'us',
  loader: {
    provide: TranslateLoader,
    useFactory: HttpLoaderFactory,
    deps: [HttpClient],
  },
});

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    graphqlProvider,
    importProvidersFrom(FormsModule),
    importProvidersFrom(HttpClientModule),
    provideAnimations(),
    httpInterceptorProvider,
    provideStore(),
    importProvidersFrom([
      HttpClientModule,
      TranslateModule.forRoot(provideTranslation())
    ]),
  ]
};
