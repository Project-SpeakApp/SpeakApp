import {Component, OnDestroy, OnInit} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {PostService} from "../../../sevices/post.service";
import {UserGet} from "../../../../../shared/types/profiles/user-get.model";
import {ReactionsGet} from "../../../../../shared/types/posts/reactions-get.model";
import {ReactionType} from "../../../../../shared/types/posts/ReactionType.enum";
import {PostGetResponse} from "../../../../../shared/types/posts/post-get-response.model";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit{

  posts: PostGet[] = [];
  userId: string = '6c84fb95-12c4-11ec-82a8-0242ac130003'; //give or get later some userId
  constructor(private postService: PostService) { }
  ngOnInit(): void {
    this.postService.getPosts(this.userId, 1, 10).subscribe((data: PostGetResponse) => {
      this.posts = data.result;
    });
  }

}

/*const user: UserGet = {
      userId: '123',
      fullName: 'John Doe',
      profilePhotoUrl: 'path-to-profile-photo'
    };

    const reactions: ReactionsGet = {
      sumOfReactions: 0,
      sumOfReactionsByType: new Map<ReactionType, number>()
    };
    const post: PostGet = {
      postId: '456',
      content: 'This is a sample post content.',
      author: user,
      createdAt: new Date(),
      reactions: reactions,
      currentUserReaction: ReactionType.LIKE
    };
    this.posts.push(post, post); */ //for tests only, put in ngOnInitMethod
