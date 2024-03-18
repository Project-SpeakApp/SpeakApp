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
  profileUpdateLoading = signal(false);

  public getProfile(userId: string) {
    this.isLoading.set(true);
    return this.http
      .get<ProfileGetDTO>(`http://localhost:8080/api/users/${userId}`)
      .pipe(
        finalize(() => this.isLoading.set(false)),
        tap(
          (r) => {console.log(r)},
          () => this.alertService.showAlert('Failed to load profile', 'error'),
        ),
      );
  }

  public updateProfile(profile: ProfileUpdateDTO) {
    this.profileUpdateLoading.set(true);
    return this.http
      .put<ProfileGetDTO>(`http://localhost:8080/api/users`, profile, {
      })
      .pipe(
        finalize(() => this.profileUpdateLoading.set(false)),
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
