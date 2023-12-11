import {Component, Input} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {ReactionsGet} from "../../../../../shared/types/posts/reactions-get.model";
import {UserGet} from "../../../../../shared/types/profiles/user-get.model";
import {ReactionType} from "../../../../../shared/types/posts/ReactionType.enum";


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent {
  //@Input() post!: PostGet;
  user: UserGet = {
    userId: '123',
    fullName: 'John Doe',
    profilePhotoUrl: 'path-to-profile-photo'
  };

  reactions: ReactionsGet = {
    sumOfReactions: 0,
    sumOfReactionsByType: new Map<ReactionType, number>()
  };
  post: PostGet = {
    postId: '456',
    content: 'This is a sample post content.',
    author: this.user,
    createdAt: new Date(),
    reactions: this.reactions,
    currentUserReaction: ReactionType.LIKE
  };

  year = this.post.createdAt.getFullYear();
  month = String(this.post.createdAt.getMonth() + 1).padStart(2, '0');
  day = String(this.post.createdAt.getDate()).padStart(2, '0');
  hours = String(this.post.createdAt.getHours()).padStart(2, '0');
  minutes = String(this.post.createdAt.getMinutes()).padStart(2, '0');
  formattedDate = `${this.year}-${this.month}-${this.day} ${this.hours}:${this.minutes}`;





}
