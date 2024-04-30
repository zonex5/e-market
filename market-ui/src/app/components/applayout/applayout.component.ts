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
import {TranslateModule, TranslatePipe, TranslateService} from "@ngx-translate/core";
import {RegisterDialogComponent} from "../register-dialog/register-dialog.component";

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
    LanguageComponent,
    TranslateModule,
    RegisterDialogComponent
  ],
  styleUrls: ['./applayout.component.css']
})
export class ApplayoutComponent implements OnInit {

  items: MenuItem[] = [
    {
      label: this.authService.userFullName,
      items: [
        {
          icon: 'pi pi-book',
          command: () => {
            this.navigate('/orders');
          }
        },
        {
          icon: 'pi pi-heart',
          command: () => {
            this.navigate('/favourites');
          }
        },
/*        {
          icon: 'pi pi-comments',
          command: () => {
            this.logout();
          }
        },*/
        {
          icon: 'pi pi-times',
          command: () => {
            this.logout();
          }
        }
      ]
    }]

  menuData: IMenuItemData[] = []
  loading = true;
  error: any;

  constructor(private qs: QueryService,
              private apollo: Apollo,
              private router: Router,
              protected cartService: CartService,
              protected authService: AuthService,
              protected translate: TranslateService
  ) { }

  get initials() {
    const userInitials = Initials(this.authService.userFullName)
    return userInitials.length > 0 ? userInitials : 'U'
  }

  ngOnInit(): void {

    this.translatePage()

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

  private translatePage() {
    this.translate.get('user.menu.orders').subscribe((res: string) => {
      this.items[0].items![0].label = res
    });
    this.translate.get('user.menu.favourites').subscribe((res: string) => {
      this.items[0].items![1].label = res
    });
    /*this.translate.get('user.menu.messages').subscribe((res: string) => {
      this.items[0].items![2].label = res
    });*/
    this.translate.get('user.menu.signout').subscribe((res: string) => {
      this.items[0].items![2].label = res
    });

  }
}
