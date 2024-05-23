import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-profile-friends-page',
  templateUrl: './user-profile-friends-page.component.html'
})
export class UserProfileFriendsPageComponent {
  userId: string = "";

  constructor(private route: ActivatedRoute) {
    this.userId = this.route.pathFromRoot[1].snapshot.paramMap.get('id') ?? "";
  }
}
