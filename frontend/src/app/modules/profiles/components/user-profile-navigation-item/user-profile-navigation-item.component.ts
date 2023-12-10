import { Component } from '@angular/core';
import { Input } from '@angular/core';

@Component({
  selector: 'app-user-profile-navigation-item',
  templateUrl: './user-profile-navigation-item.component.html'
})
export class UserProfileNavigationItemComponent {
  @Input() icon: string = "";
  @Input() label: string = "";
  @Input() route: string = "";
}
