import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-user-profile-info-label',
  templateUrl: './user-profile-info-label.component.html'
})
export class UserProfileInfoLabelComponent {
  @Input() label: string = "";
  @Input() value: string = "";
}
