import {Component, Input} from '@angular/core';
import {AuthService} from "../../../../shared/services/auth.service";

@Component({
  selector: 'app-user-profile-navigation',
  templateUrl: './user-profile-navigation.component.html'
})
export class UserProfileNavigationComponent {
  @Input() userId: string = '';

  authState = this.authService.state;

  constructor(private authService: AuthService) {
  }
}
