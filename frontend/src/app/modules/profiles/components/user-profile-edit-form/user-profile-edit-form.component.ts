import { Component, Input } from '@angular/core';
import ProfileGetDTO from '../../types/ProfileGetDTO';

@Component({
  selector: 'app-user-profile-edit-form',
  templateUrl: './user-profile-edit-form.component.html'
})
export class UserProfileEditFormComponent {
  @Input() profile: ProfileGetDTO | null = null;
  @Input() isLoading = false;

}
