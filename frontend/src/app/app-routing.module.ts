import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './shared/home-page/home-page.component';
import { UserProfilePageComponent } from './modules/profiles/components/user-profile-page/user-profile-page.component';
import { NotFoundPageComponent } from './shared/not-found-page/not-found-page.component';
import { UserProfileInfoPageComponent } from './modules/profiles/components/user-profile-info-page/user-profile-info-page.component';
import { UserProfileSettingsPageComponent } from './modules/profiles/components/user-profile-settings-page/user-profile-settings-page.component';
import {PostFeedPageComponent} from "./modules/posts/components/post-feed-page/post-feed-page.component";
import {AuthGuard} from "./guard/auth.guard";




const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'profiles/:id', component: UserProfilePageComponent, canActivate: [AuthGuard], children: [
    { path: 'info', component: UserProfileInfoPageComponent  },
  ]},
  { path: 'profiles/:id/settings', component: UserProfileSettingsPageComponent, canActivate: [AuthGuard] },
  { path: 'posts', component: PostFeedPageComponent, canActivate: [AuthGuard]},
  { path: '**', component: NotFoundPageComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
