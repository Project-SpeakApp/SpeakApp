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

  loadPosts() {
    this.isLoading = true;
    const userId = this.authService.state().userId;
    this.subscription = this.postService.getPosts(userId, this.pageNumber, 10).subscribe({
      next: (response) => {
        // i have suicide thoughts and this point
        response.posts.forEach((post) => {
          const keys = Object.keys(post.reactions.sumOfReactionsByType);
          const value = Object.values(post.reactions.sumOfReactionsByType);
          post.reactions.sumOfReactionsByType = new Map();
          for (let i = 0; i < keys.length; i++) {
            const reactionType: ReactionType = ReactionType[keys[i] as keyof typeof ReactionType];
            post.reactions.sumOfReactionsByType.set(reactionType, value[i]);
          }
        });
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
      this.posts.unshift(newPost);
    }
  }

  handleDeletion(postToDelete: string) {
    if (postToDelete) {
      this.posts = this.posts.filter((post) => post.postId !== postToDelete);
    }
  }

  changeReaction({ reaction, postId }: { reaction: ReactionType | null; postId: string }) {
    const index = this.posts.findIndex((post) => post.postId === postId);
    const oldReaction = this.posts[index].currentUserReaction;
    this.posts[index].currentUserReaction = reaction;

    // update overall count
    if (oldReaction === null && reaction !== null) {
      this.posts[index].reactions.sumOfReactions++;
    } else if (oldReaction !== null && reaction === null) {
      this.posts[index].reactions.sumOfReactions--;
    }

    // update reaction type count
    if (oldReaction !== null) {
      const currentCount = this.posts[index].reactions.sumOfReactionsByType.get(oldReaction);
      console.log(currentCount);
      this.posts[index].reactions.sumOfReactionsByType.set(
        oldReaction,
        this.posts[index].reactions.sumOfReactionsByType.get(oldReaction)! - 1,
      );
    }
    if (reaction !== null) {
      const currentCount = this.posts[index].reactions.sumOfReactionsByType.get(reaction);
      if (currentCount) {
        this.posts[index].reactions.sumOfReactionsByType.set(reaction, currentCount + 1);
      } else {
        this.posts[index].reactions.sumOfReactionsByType.set(reaction, 1);
      }
    }
  }

  ngOnInit() {
    this.loadPosts();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
