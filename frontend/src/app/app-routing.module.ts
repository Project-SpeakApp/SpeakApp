import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './shared/home-page/home-page.component';
<<<<<<< HEAD
import { UserProfilePageComponent } from './modules/profiles/components/user-profile-page/user-profile-page.component';
import { UserProfileInfoComponent } from './modules/profiles/components/user-profile-info/user-profile-info.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'profiles/:id', component: UserProfilePageComponent, children: [
    { path: 'info', component: UserProfileInfoComponent  },
  ] }
=======
import {AddPostComponent} from "./modules/posts/components/add-post/add-post.component";

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'posts/create', component: AddPostComponent},
>>>>>>> 63183ca1f92cb44a770badbe4069af22551fc25b
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
