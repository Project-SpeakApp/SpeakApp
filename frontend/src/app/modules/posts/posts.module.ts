import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPostComponent } from './components/add-post/add-post.component';
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import { PostComponent } from './components/post/post/post.component';
import { PostListComponent } from './components/post-list/post-list/post-list.component';
import { DeletePostComponent } from './components/delete-post/delete-post.component';



@NgModule({
  declarations: [
    AddPostComponent,
    PostComponent,
    PostListComponent,
    DeletePostComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule

  ],
})
export class PostsModule { }
