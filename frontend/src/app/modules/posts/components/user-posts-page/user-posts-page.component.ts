import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-posts-page',
  templateUrl: './user-posts-page.component.html'
})
export class UserPostsPageComponent {
  userId: string = "";

  constructor(private route: ActivatedRoute) {
    this.userId = this.route.pathFromRoot[1].snapshot.paramMap.get('id') ?? "";
  }
}
