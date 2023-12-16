import { Component, Input } from '@angular/core';
import ProfileGetDTO from '../../types/ProfileGetDTO';

@Component({
  selector: 'app-user-profile-info',
  templateUrl: './user-profile-info.component.html'
})
export class UserProfileInfoComponent {
  @Input() profile: ProfileGetDTO | null = null;
  @Input() isLoading = false;
}
