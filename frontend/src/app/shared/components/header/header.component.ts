import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ThemeService } from '../../services/theme.service';
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private themeService: ThemeService, private keycloak: KeycloakService) { }

  authState = this.authService.state;

  themes = this.themeService.themes;

  themeChanges(event: any) {
    this.themeService.changeTheme(event.target.value);
    console.log(event.target.value);
  }

  async logoutUser() {
    await this.keycloak.logout('http://localhost:4200/');
  }

  async loginUser() {
    await this.keycloak.login();
  }

  async registerUser() {
    await this.keycloak.register();
  }

  ngOnInit(): void {

  }
}
