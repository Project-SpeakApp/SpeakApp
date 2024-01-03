import {Component, OnDestroy, OnInit} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {PostService} from "../../../sevices/post.service";
import {UserGet} from "../../../../../shared/types/profiles/user-get.model";
import {ReactionsGet} from "../../../../../shared/types/posts/reactions-get.model";
import {ReactionType} from "../../../../../shared/types/posts/ReactionType.enum";
import {PostGetResponse} from "../../../../../shared/types/posts/post-get-response.model";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../../shared/services/auth.service";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit, OnDestroy{

  private addPostSubscription?: Subscription;

  posts: PostGet[] = [];

  isLoading = this.postService.isLoadingGet;

  constructor(private postService: PostService, private authService: AuthService) { }
  ngOnInit(): void {
    this.addPostSubscription = this.postService.getPosts(this.authService.state().userId, 0, 7).subscribe((data: PostGetResponse) => {
      this.posts = data.posts;
    });
  }

  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }

}


