import {Component,OnInit} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {PostService} from "../../../sevices/post.service";
import {AuthService} from "../../../../../shared/services/auth.service";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit{

  posts: PostGet[] = [];

  isLoading = false;

  constructor(private postService: PostService, private authService: AuthService) { }
  ngOnInit() {
    this.loadPosts();
  }
  loadPosts() {
    this.isLoading = true;
    const userId = this.authService.state().userId;
    this.postService.getPosts(userId, 0, 5).subscribe({
      next: (response) => {
        this.posts = response.posts;
        this.isLoading = false;
      },
      error: (error) => {
        this.isLoading = false;
      }
    });
  }

  addContent(newPost?: PostGet): void {
    if(newPost) {
      this.posts.unshift(newPost);
    }
  }


  handleDeletion(postToDelete: string) {
    if(postToDelete) {
      this.posts = this.posts.filter(post => post.postId !== postToDelete);
    }
  }
}


