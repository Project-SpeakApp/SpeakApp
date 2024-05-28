import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './shared/home-page/home-page.component';
import { UserProfilePageComponent } from './modules/profiles/components/user-profile-page/user-profile-page.component';
import { NotFoundPageComponent } from './shared/not-found-page/not-found-page.component';
import { UserProfileInfoPageComponent } from './modules/profiles/components/user-profile-info-page/user-profile-info-page.component';
import { UserProfileSettingsPageComponent } from './modules/profiles/components/user-profile-settings-page/user-profile-settings-page.component';
import {PostFeedPageComponent} from "./modules/posts/components/post-feed-page/post-feed-page.component";
import {AuthGuard} from "./guard/auth.guard";
import {MainViewComponent} from "./modules/chat/components/main-view/main-view.component";
import {UserPostsPageComponent} from "./modules/posts/components/user-posts-page/user-posts-page.component";
import {
  UserFavoritePostsPageComponent
} from "./modules/posts/components/user-favorite-posts-page/user-favorite-posts-page.component";
import { UserProfileFriendsPageComponent } from './modules/profiles/components/user-profile-friends-page/user-profile-friends-page.component';
import {FriendsPageComponent} from "./modules/posts/components/friends-page/friends-page.component";




const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'profiles/:id', component: UserProfilePageComponent, canActivate: [AuthGuard], children: [
    { path: 'info', component: UserProfileInfoPageComponent  },
    { path: 'posts', component: UserPostsPageComponent },
    { path: 'saved', component: UserFavoritePostsPageComponent },
    { path: 'friends', component: UserProfileFriendsPageComponent },
  ]},
  { path: 'profiles/:id/settings', component: UserProfileSettingsPageComponent, canActivate: [AuthGuard] },
  { path: 'posts', component: PostFeedPageComponent, canActivate: [AuthGuard]},
  {path: 'chats', component:MainViewComponent, canActivate: [AuthGuard]},
  { path: 'friends', component: FriendsPageComponent, canActivate: [AuthGuard]},
  { path: '**', component: NotFoundPageComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
