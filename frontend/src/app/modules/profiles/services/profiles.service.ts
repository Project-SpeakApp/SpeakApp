import { Injectable, signal } from '@angular/core';
import { AlertService } from 'src/app/shared/services/alert.service';
import { HttpClient } from '@angular/common/http';
import ProfileGetDTO from '../types/ProfileGetDTO';
import { finalize, tap } from 'rxjs';
import ProfileUpdateDTO from '../types/ProfileUpdateDTO';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class ProfilesService {
  constructor(
    private alertService: AlertService,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
  ) {}

  isLoading = signal(false);

  public getProfile(userId: string) {
    this.isLoading.set(true);
    return this.http
      .get<ProfileGetDTO>(`http://localhost:8081/api/users/${userId}`)
      .pipe(
        finalize(() => this.isLoading.set(false)),
        tap(
          () => {},
          () => this.alertService.showAlert('Failed to load profile', 'error'),
        ),
      );
  }

  public updateProfile(profile: ProfileUpdateDTO) {
    this.isLoading.set(true);
    return this.http
      .put<ProfileGetDTO>(`http://localhost:8081/api/users`, profile, {
        headers: { UserId: this.authService.state().userId },
      })
      .pipe(
        finalize(() => this.isLoading.set(false)),
        tap(
          () => {
            this.alertService.showAlert('Profile updated', 'success');
            this.router.navigate(['/profiles', this.authService.state().userId]);
          },
          () =>
            this.alertService.showAlert('Failed to update profile', 'error'),
        ),
      );
  }
}
