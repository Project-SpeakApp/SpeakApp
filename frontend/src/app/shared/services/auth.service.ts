import { Injectable, signal } from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {ProfilesService} from "../../modules/profiles/services/profiles.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private keycloak: KeycloakService) { }

  defaultState: AuthState = {
    isLoggedIn: false,
    firstName: 'Christopher',
    lastName: 'Bear',
    userId: '6c84fb97-12c4-11ec-82a8-0242ac130003'
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
}

type AuthState = {
  isLoggedIn: boolean;
  firstName: string;
  lastName: string;
  userId: string;
}
