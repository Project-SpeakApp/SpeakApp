import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-user-profile-info-birthday-card',
  templateUrl: './user-profile-info-birthday-card.component.html'
})
export class UserProfileInfoBirthdayCardComponent {
  @Input() birthday: string = "";
}
