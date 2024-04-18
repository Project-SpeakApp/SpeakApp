import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';
import {AuthService} from "../shared/services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

  constructor(
    protected readonly routerr: Router,
    protected readonly keycloak: KeycloakService,
    private readonly authService: AuthService
  ) {
    super(routerr,keycloak);
  }

  async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean | UrlTree> {

    if (!this.authenticated) {
      await this.authService.login({
        redirectUri: window.location.origin + state.url,
      });
    }

    return this.authenticated;
  }
}
