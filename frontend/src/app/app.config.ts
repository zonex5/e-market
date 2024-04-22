import {ApplicationConfig, importProvidersFrom, Provider} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {HTTP_INTERCEPTORS, HttpClientModule, provideHttpClient} from '@angular/common/http';
import {graphqlProvider} from './graphql.provider';
import {registerLocaleData} from '@angular/common';
import en from '@angular/common/locales/en';
import {FormsModule} from '@angular/forms';
import {provideAnimations} from '@angular/platform-browser/animations';
import {HeaderHttpInterceptor} from "./interceptor/http.interceptor";
import {provideStore} from '@ngrx/store';

registerLocaleData(en);

const httpInterceptorProvider: Provider =
  {provide: HTTP_INTERCEPTORS, useClass: HeaderHttpInterceptor, multi: true};

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
    provideHttpClient(),
    graphqlProvider,
    importProvidersFrom(FormsModule),
    importProvidersFrom(HttpClientModule),
    provideAnimations(),
    httpInterceptorProvider,
    provideStore()]
};
