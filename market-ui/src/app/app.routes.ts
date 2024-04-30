import {Routes} from '@angular/router';
import {MainMarketPageComponent} from "./pages/main-market-page/main-market-page.component";
import {ProductsMarketPageComponent} from "./pages/products-market-page/products-market-page.component";
import {ProductDetailsMarketPageComponent} from "./pages/product-details-market-page/product-details-market-page.component";
import {ProductsSearchMarketPageComponent} from "./pages/products-search-market-page/products-search-market-page.component";
import {ShoppingCartComponent} from "./pages/shopping-cart/shopping-cart.component";
import {LoginComponent} from "./pages/login-page/login.component";
import {CheckoutComponent} from "./pages/checkout/checkout.component";
import {authGuard} from "./guards/auth.guard";
import {FavouriteProductsComponent} from "./pages/favourite-products/favourite-products.component";
import {PrivacyPageComponent} from "./pages/footer-pages/privacy-page/privacy-page.component";
import {ReturnPolicyPageComponent} from "./pages/footer-pages/return-policy-page/return-policy-page.component";
import {TermsAndConditionsComponent} from "./pages/footer-pages/terms-and-conditions/terms-and-conditions.component";
import {OrdersPageComponent} from "./pages/orders-page/orders-page.component";
import {OrdersListComponent} from "./components/orders-list/orders-list.component";

export const routes: Routes = [
  {path: '', redirectTo: 'main', pathMatch: 'full'},
  {path: 'main', component: MainMarketPageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'category/:id', component: ProductsMarketPageComponent},
  {path: 'product/:id', component: ProductDetailsMarketPageComponent},
  {path: 'search', component: ProductsSearchMarketPageComponent},
  {path: 'cart', component: ShoppingCartComponent, canActivate: [authGuard]},
  {path: 'checkout', component: CheckoutComponent, canActivate: [authGuard]},
  {path: 'favourites', component: FavouriteProductsComponent, canActivate: [authGuard]},
  {
    path: 'orders', component: OrdersPageComponent, canActivate: [authGuard],
    children: [
      {path: '', redirectTo: 'list/all', pathMatch: 'full'},
      {path: 'list/:type', component: OrdersListComponent},
      /*{path: '**', redirectTo: 'list/all'}*/

      /*{path: '', redirectTo: 'all', pathMatch: 'full'},
      {path: 'all', component: OrdersListComponent},
      {path: 'active', component: OrdersListComponent},
      {path: 'new', component: OrdersListComponent},
      {path: 'shipped', component: OrdersListComponent},
      {path: 'completed', component: OrdersListComponent}*/
    ]
  },
  {
    path: 'info',
    children: [
      {path: '', redirectTo: 'privacy', pathMatch: 'full'},
      {path: 'privacy', component: PrivacyPageComponent},
      {path: 'return-policy', component: ReturnPolicyPageComponent},
      {path: 'terms', component: TermsAndConditionsComponent}
    ]
  }
];
