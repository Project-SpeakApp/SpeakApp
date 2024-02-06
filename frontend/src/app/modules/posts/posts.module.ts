import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPostComponent } from './components/add-post/add-post.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import { PostComponent } from './components/post/post/post.component';
import { PostListComponent } from './components/post-list/post-list/post-list.component';
import { DeletePostComponent } from './components/delete-post/delete-post.component';
import {SharedModule} from "../../shared/shared.module";
import { PostFeedPageComponent } from './components/post-feed-page/post-feed-page.component';
import { EditPostComponent } from './components/edit-post/edit-post.component';
import { PostBottomBarComponent } from './components/post-bottom-bar/post-bottom-bar.component';
import { CommentComponent } from './components/comment/comment.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import { LikeButtonComponent } from './components/like-button/like-button.component';
import { ReactionsDisplayComponent } from './components/reactions-display/reactions-display.component';



@NgModule({
  declarations: [
    AddPostComponent,
    PostComponent,
    PostListComponent,
    DeletePostComponent,
    PostFeedPageComponent,
    EditPostComponent,
    PostBottomBarComponent,
    CommentComponent,
    CommentListComponent,
    LikeButtonComponent,
    ReactionsDisplayComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    SharedModule,
    InfiniteScrollModule,

  ],
  exports: [
    AddPostComponent,
    DeletePostComponent,
    PostListComponent
  ]
})
export class PostsModule { }
