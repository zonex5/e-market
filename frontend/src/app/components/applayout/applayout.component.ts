import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {QueryService} from "../../graphql/query.service";
import {Apollo} from "apollo-angular";
import {AsyncPipe, CommonModule} from "@angular/common";
import {ButtonModule} from 'primeng/button';
import {MenubarModule} from "primeng/menubar";
import {ToolbarModule} from "primeng/toolbar";
import {SplitButtonModule} from "primeng/splitbutton";
import {InputTextModule} from "primeng/inputtext";
import {MegaMenuModule} from "primeng/megamenu";
import {SidebarModule} from "primeng/sidebar";
import {CartService} from "../../service/cart.service";
import {ToStringPipe} from "../../pipes/to-string.pipe";
import {FooterComponent} from "../footer/footer.component";
import {MenuModule} from "primeng/menu";
import {AuthService} from "../../service/auth.service";
import {MenuItem} from "primeng/api";
import {BadgeModule} from "primeng/badge";
import {LoginDialogComponent} from "../login-dialog/login-dialog.component";
import {ToBoolPipe} from "../../pipes/to-bool.pipe";
import {AvatarModule} from "primeng/avatar";
import {Initials} from "../../helpers/string-utils";
import {LanguageComponent} from "../language/language.component";

interface IMenuItemData {
  id: string
  title: string
}

@Component({
  selector: 'app-applayout',
  templateUrl: './applayout.component.html',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterOutlet,
    RouterLinkActive,
    ButtonModule,
    MenubarModule,
    ToolbarModule,
    SplitButtonModule,
    InputTextModule,
    MegaMenuModule,
    SidebarModule,
    ToStringPipe,
    AsyncPipe,
    FooterComponent,
    LanguageComponent,
    MenuModule,
    BadgeModule,
    LoginDialogComponent,
    ToBoolPipe,
    AvatarModule,
    LanguageComponent
  ],
  styleUrls: ['./applayout.component.css']
})
export class ApplayoutComponent implements OnInit {

  items: MenuItem[] | undefined;

  menuData: IMenuItemData[] = []
  loading = true;
  error: any;

  constructor(private qs: QueryService,
              private apollo: Apollo,
              private router: Router,
              protected cartService: CartService,
              protected authService: AuthService
  ) { }

  get initials() {
    return Initials(this.authService.userFullName)
  }

  ngOnInit(): void {

    // set user info to menu
    this.items = [
      {
        label: this.authService.userFullName,
        items: [
          {
            label: 'My Orders',
            icon: 'pi pi-book',
            command: () => {
              this.navigate('/orders');
            }
          },
          {
            label: 'My Favourites',
            icon: 'pi pi-heart',
            command: () => {
              this.navigate('/favourites');
            }
          },
          {
            label: 'My Messages',
            icon: 'pi pi-comments',
            command: () => {
              this.logout();
            }
          },
          {
            label: 'Sign out',
            icon: 'pi pi-times',
            command: () => {
              this.logout();
            }
          }
        ]
      }]

    this.apollo.watchQuery({
      query: this.qs.queryMenuCategories
    })
      .valueChanges.subscribe(({data, loading, error}) => {
      this.menuData = (data as any).getActiveCategories
      this.loading = loading
      this.error = error
    })
  }

  private navigate(s: string) {
    this.router.navigate([s])
  }

  private logout() {
    this.authService.logout().subscribe({
      complete: () => {
        location.reload()
      }
    })
  }

  login() {
    this.authService.showLoginDialog()
  }
}
