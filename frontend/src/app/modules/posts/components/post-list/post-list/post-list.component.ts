import {Component, OnDestroy, OnInit} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {PostService} from "../../../sevices/post.service";
import {UserGet} from "../../../../../shared/types/profiles/user-get.model";
import {ReactionsGet} from "../../../../../shared/types/posts/reactions-get.model";
import {ReactionType} from "../../../../../shared/types/posts/ReactionType.enum";
import {PostGetResponse} from "../../../../../shared/types/posts/post-get-response.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit, OnDestroy{
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

  post2: PostGet = {
    postId: '4567',
    content: 'This is a sample post content.',
    author: this.user,
    createdAt: new Date(),
    reactions: this.reactions,
    currentUserReaction: ReactionType.LIKE
  };

  private addPostSubscription?: Subscription;

  posts: PostGet[] = [];
  userId: string = '6c84fb95-12c4-11ec-82a8-0242ac130003'; //give or get later some userId

  isLoading = this.postService.isLoadingGet;

  constructor(private postService: PostService) { }
  ngOnInit(): void {
    this.posts.push(this.post, this.post2);
    /*this.addPostSubscription = this.postService.getPosts(this.userId, 1, 10).subscribe((data: PostGetResponse) => {
      this.posts = data.result; // jak bedzie endpoint trzeba to odkomentowac i usunac zmienne utworzone powyzej
    });*/
  }

  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }

}


