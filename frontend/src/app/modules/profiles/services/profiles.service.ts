import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import ProfileGetDTO from '../types/ProfileGetDTO';
import { finalize, tap } from 'rxjs';
import ProfileUpdateDTO from '../types/ProfileUpdateDTO';

@Injectable({
  providedIn: 'root',
})
export class ProfilesService {
  constructor(
    private http: HttpClient,
  ) {}

  isLoading = signal(false);
  profileUpdateLoading = signal(false);

  public getProfile(userId: string) {
    this.isLoading.set(true);
    return this.http
      .get<ProfileGetDTO>(`https://localhost:4200/api/users/${userId}`)
      .pipe(
        finalize(() => this.isLoading.set(false)),
      );
  }

  public updateProfile(profile: ProfileUpdateDTO) {
    this.profileUpdateLoading.set(true);
    return this.http
      .put<ProfileGetDTO>(`https://localhost:4200/api/users`, profile, {
      })
      .pipe(
        finalize(() => this.profileUpdateLoading.set(false)),
        tap(
          () => {},
        ),
      );
  }

  public updateProfilePhoto(mediaId: string) {
    return this.http.put(`https://localhost:4200/api/users/profile-photo`, { photoId: mediaId }, {});
  }

  public updateBackgroundPhoto(mediaId: string) {
    return this.http.put(`https://localhost:4200/api/users/background-photo`, { photoId: mediaId }, {});
  }
}
