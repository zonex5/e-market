import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ApplayoutComponent} from "./components/applayout/applayout.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ApplayoutComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'vladik-market-ui';
}
