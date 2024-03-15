import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ThemeService } from '../../services/theme.service';
import {KeycloakService} from "keycloak-angular";

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

  async logoutUser() {
    await this.authService.logout('http://localhost:4200/');
  }

  async loginUser() {
    await this.authService.login();
  }

  async registerUser() {
    await this.authService.register();
  }

  ngOnInit(): void {

  }
}
