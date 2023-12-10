import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './shared/home-page/home-page.component';
import {AddPostComponent} from "./modules/posts/components/add-post/add-post.component";

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'posts/create', component: AddPostComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
