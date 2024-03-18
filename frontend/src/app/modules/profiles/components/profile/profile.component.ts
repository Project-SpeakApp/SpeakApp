import { Component, Input } from '@angular/core';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import {AuthService} from "../../../../shared/services/auth.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent {
  @Input() profile: ProfileGetDTO | null = null;
  @Input() isLoading = false;

  constructor(private authService: AuthService) {}

  changePassword() {
    this.authService.manageAccount();
  }
}
