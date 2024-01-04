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

  isLoading = this.postService.isLoadingGet;

  constructor(private postService: PostService, private authService: AuthService) { }
  ngOnInit() {

    this.postService.posts$.subscribe(posts => {
      this.posts = posts;
    });
    this.postService.getPosts(this.authService.state().userId, 0, 5).subscribe();

  }





}


