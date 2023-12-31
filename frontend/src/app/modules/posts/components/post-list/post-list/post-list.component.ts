import { Component, OnDestroy, OnInit } from '@angular/core';
import { PostGet } from '../../../../../shared/types/posts/post-get.model';
import { PostService } from '../../../sevices/post.service';
import { AuthService } from '../../../../../shared/services/auth.service';
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

  subscription = new Subscription();

  constructor(
    private postService: PostService,
    private authService: AuthService,
  ) {}

  onScroll() {
    this.loadPosts();
  }

  private parsePosts(posts: PostGet[]) {
    // i have suicide thoughts and this point
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
    this.isLoading = true;
    const userId = this.authService.state().userId;
    this.subscription = this.postService.getPosts(userId, this.pageNumber, 10).subscribe({
      next: (response) => {
        this.parsePosts(response.posts);
        this.posts = [...this.posts, ...response.posts];
        this.pageNumber = response.currentPage + 1;
        this.isLoading = false;
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
