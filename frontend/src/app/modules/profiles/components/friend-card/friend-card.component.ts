import {Component, Input} from '@angular/core';
import {UserGet} from "../../../../shared/types/profiles/user-get.model";

@Component({
  selector: 'app-friend-card',
  templateUrl: './friend-card.component.html'
})
export class FriendCardComponent {

  @Input() user: UserGet = {} as UserGet;
}
