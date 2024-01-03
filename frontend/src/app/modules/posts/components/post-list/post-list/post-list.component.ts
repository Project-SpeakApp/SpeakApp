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
export class PostListComponent implements OnInit{

  private addPostSubscription?: Subscription;

  posts: PostGet[] = [];

  isLoading = this.postService.isLoadingGet;

  constructor(private postService: PostService, private authService: AuthService) { }
  ngOnInit() {
    this.postService.posts$.subscribe(updatedPosts => {
      this.posts = updatedPosts;
    });
    this.postService.refreshPosts(this.authService.state().userId); // Load initial posts

  }





}


