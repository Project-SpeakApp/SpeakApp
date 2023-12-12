import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './shared/home-page/home-page.component';
import {AddPostComponent} from "./modules/posts/components/add-post/add-post.component";

import { UserProfilePageComponent } from './modules/profiles/components/user-profile-page/user-profile-page.component';
import { UserProfileInfoComponent } from './modules/profiles/components/user-profile-info/user-profile-info.component';
import {PostComponent} from "./modules/posts/components/post/post/post.component";
import {PostListComponent} from "./modules/posts/components/post-list/post-list/post-list.component";
import { NotFoundPageComponent } from './shared/not-found-page/not-found-page.component';
import { UserProfileInfoPageComponent } from './modules/profiles/components/user-profile-info-page/user-profile-info-page.component';




const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'profiles/:id', component: UserProfilePageComponent, children: [
    { path: 'info', component: UserProfileInfoComponent  },
  ]},
  { path: '**', component: NotFoundPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
