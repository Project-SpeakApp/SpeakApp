import { Injectable, signal } from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {ProfilesService} from "../../modules/profiles/services/profiles.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private keycloak: KeycloakService, private profileService: ProfilesService) { }

  defaultState: AuthState = {
    isLoggedIn: false,
    firstName: '',
    lastName: '',
    userId: '',
    profilePhotoId: '',
  }

  state = signal(this.defaultState);

  requestCount = signal(0);

  public updateState() {
    try {
      this.keycloak.loadUserProfile().then(x => {
        this.profileService.getProfile(x.id!).subscribe((res) => {
          this.state.set({
            isLoggedIn: true,
            firstName: res.firstName,
            lastName: res.lastName,
            userId: x.id!,
            profilePhotoId: res.profilePhotoId,
          });
        });
      });
    }
    catch (_) {
      this.state.set(this.defaultState);
    }
  }

  public updateRequestsCount(amount: number) {
    this.requestCount.update((s) => s - amount);
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

  public updateProfilePhoto(photoId: string) {
    this.state.set({
      ...this.state(),
      profilePhotoId: photoId,
    })
  }
}

type AuthState = {
  isLoggedIn: boolean;
  firstName: string;
  lastName: string;
  userId: string;
  profilePhotoId: string;
}
