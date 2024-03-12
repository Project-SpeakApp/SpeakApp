import { Injectable, signal } from '@angular/core';
import {KeycloakService} from "keycloak-angular";

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

  public updateState(firstName: string, lastName: string) {
    this.keycloak.loadUserProfile().then(profile => console.log(profile));
    this.state.set({
      ...this.state(),
      firstName,
      lastName,
    });
  }
}

type AuthState = {
  isLoggedIn: boolean;
  firstName: string;
  lastName: string;
  userId: string;
}
