import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private themeService: ThemeService) { }

  authState = this.authService.state;

  themes = this.themeService.themes;

  themeChanges(event: any) {
    this.themeService.changeTheme(event.target.value);
    console.log(event.target.value);
  }

  ngOnInit(): void {

  }
}
