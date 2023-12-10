import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { HomePageComponent } from './shared/home-page/home-page.component';
import { AlertListComponent } from './shared/components/alert-list/alert-list.component';
<<<<<<< HEAD
import { ProfilesModule } from './modules/profiles/profiles.module';
=======
import {FormsModule} from "@angular/forms";
import {AddPostComponent} from "./modules/posts/components/add-post/add-post.component";
>>>>>>> 63183ca1f92cb44a770badbe4069af22551fc25b
import {PostsModule} from "./modules/posts/posts.module";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomePageComponent,
    AlertListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
<<<<<<< HEAD
    ProfilesModule,
=======
    FormsModule,
    PostsModule
>>>>>>> 63183ca1f92cb44a770badbe4069af22551fc25b
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
