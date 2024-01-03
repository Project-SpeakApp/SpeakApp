import { Component } from '@angular/core';
import { ProfilesService } from '../../services/profiles.service';
import { Observable } from 'rxjs';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-profile-settings-page',
  templateUrl: './user-profile-settings-page.component.html'
})
export class UserProfileSettingsPageComponent {

  constructor(private profilesService: ProfilesService, private route: ActivatedRoute) {}

  profile$: Observable<ProfileGetDTO> = new Observable();
  isLoading = this.profilesService.isLoading;

  ngOnInit(): void {
    const userId = this.route.snapshot.paramMap.get('id');
    if (!userId) {
      console.log('No user id');
      return;
    }
    this.profile$ = this.profilesService.getProfile(userId);
  }
}
