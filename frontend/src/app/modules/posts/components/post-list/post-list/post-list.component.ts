import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import { PostGet } from '../../../../../shared/types/posts/post-get.model';
import { PostService } from '../../../sevices/post.service';
import { ReactionType } from 'src/app/shared/types/posts/ReactionType.enum';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css'],
})
export class PostListComponent implements OnInit, OnDestroy {
  posts: PostGet[] = [];
  isLoading = false;
  pageNumber: number = 0;

  totalPages: number = 0;

  subscription = new Subscription();

  @Input() userId?: string;
  @Input() favouritePosts?: boolean;

  constructor(
    private postService: PostService,
  ) {}

  onScroll() {
    this.loadPosts();
  }

  private parsePosts(posts: PostGet[]) {
    posts.forEach((post) => {
      const keys = Object.keys(post.reactions.sumOfReactionsByType);
      const value = Object.values(post.reactions.sumOfReactionsByType);

      post.reactions.sumOfReactionsByType = new Map();

      for (let i = 0; i < keys.length; i++) {
        const reactionType: ReactionType = ReactionType[keys[i] as keyof typeof ReactionType];
        post.reactions.sumOfReactionsByType.set(reactionType, value[i]);
      }
    })
  }

  loadPosts() {
    if(this.totalPages === this.pageNumber && this.pageNumber !== 0) return;
    this.isLoading = true;
    this.subscription = this.postService.getPosts(this.pageNumber, 10, this.favouritePosts, this.userId).subscribe({
      next: (response) => {
        this.parsePosts(response.posts);
        this.posts = [...this.posts, ...response.posts];
        this.pageNumber = response.currentPage + 1;
        this.isLoading = false;
        this.totalPages = response.totalPages;
      },
      error: (error) => {
        this.isLoading = false;
      },
    });
  }

  addContent(newPost?: PostGet): void {
    if (newPost) {
      this.parsePosts([newPost]);
      this.posts.unshift(newPost);
    }
  }

  handleDeletion(postToDelete: string) {
    if (postToDelete) {
      this.posts = this.posts.filter((post) => post.postId !== postToDelete);
    }
  }

  ngOnInit() {
    this.loadPosts();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
