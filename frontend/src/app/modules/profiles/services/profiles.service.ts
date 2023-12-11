import { Injectable, signal } from '@angular/core';
import { AlertService } from 'src/app/shared/services/alert.service';
import { HttpClient } from '@angular/common/http';
import ProfileGetDTO from '../types/ProfileGetDTO';
import { finalize, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfilesService {

  constructor(private alertService: AlertService, private http: HttpClient) { }

  isLoading = signal(false);

  public getProfile(userId: string) {
    this.isLoading.set(true);
    return this.http.get<ProfileGetDTO>(`http://localhost:8081/api/users/${userId}`)
      .pipe(
        finalize(() => this.isLoading.set(false)),
        tap(
          () => {},
          () => this.alertService.showAlert('Failed to load profile', 'error')
        )
      )
  }
}
