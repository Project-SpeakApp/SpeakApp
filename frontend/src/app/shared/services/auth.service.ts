import { Injectable, signal } from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private keycloak: KeycloakService, private router: Router) { }

  defaultState: AuthState = {
    isLoggedIn: false,
    firstName: '',
    lastName: '',
    userId: ''
  }

  state = signal(this.defaultState);

  public updateState() {
    try {
      this.keycloak.loadUserProfile().then(x => {
        this.state.set({
          isLoggedIn: true,
          firstName: x.firstName!,
          lastName: x.lastName!,
          userId: x.id!
        });
      });
    }
    catch (_) {
      this.state.set(this.defaultState);
    }
  }

  public async login(options?: Keycloak.KeycloakLoginOptions) {
    await this.keycloak.login(options);
    this.updateState();
  }

  public async logout(redirectUri?: string) {
    await this.keycloak.logout(redirectUri);
    this.updateState();
  }

  public async register(options?: Keycloak.KeycloakLoginOptions) {
    await this.keycloak.register();
    this.updateState();
  }

  public manageAccount() {
    window.location.href = 'http://localhost:8443/realms/SpeakApp/account/#/security/signingin';
  }
}

type AuthState = {
  isLoggedIn: boolean;
  firstName: string;
  lastName: string;
  userId: string;
}
