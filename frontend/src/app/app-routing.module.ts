import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './shared/home-page/home-page.component';
import {AddPostComponent} from "./modules/posts/components/add-post/add-post.component";

import { UserProfilePageComponent } from './modules/profiles/components/user-profile-page/user-profile-page.component';
import { UserProfileInfoComponent } from './modules/profiles/components/user-profile-info/user-profile-info.component';
import {PostComponent} from "./modules/posts/components/post/post/post.component";
import {PostListComponent} from "./modules/posts/components/post-list/post-list/post-list.component";




const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'posts/create', component: AddPostComponent},
  { path: 'profiles/:id', component: UserProfilePageComponent, children: [
    { path: 'info', component: UserProfileInfoComponent  },
  ]},
  { path: 'post/get', component: PostListComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
