import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {UserGet} from "../../../../shared/types/profiles/user-get.model";

@Component({
  selector: 'app-author-preview',
  templateUrl: './author-preview.component.html'
})
export class AuthorPreviewComponent {
  @Input() formattedDate: string = '';
  @Input() user: UserGet = {} as UserGet;
  @Input() isComment: boolean = false;

  constructor(private router: Router) {
  }
  async navigateToUserProfile() {
    await this.router.navigate(['/profiles', this.user.userId]);
  }
}
