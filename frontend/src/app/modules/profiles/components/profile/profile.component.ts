import { Component, Input } from '@angular/core';
import ProfileGetDTO from '../../types/ProfileGetDTO';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent {
  @Input() profile: ProfileGetDTO | null = null;
  @Input() isLoading = false;
}
