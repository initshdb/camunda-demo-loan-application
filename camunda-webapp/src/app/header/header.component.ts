import { ChangeDetectionStrategy, Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {MatToolbar} from '@angular/material/toolbar';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'camunda-header',
  imports: [RouterLink, RouterLinkActive, MatToolbar, MatButton],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent {

}
