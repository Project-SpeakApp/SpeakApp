import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { HomePageComponent } from './shared/home-page/home-page.component';
import { AlertListComponent } from './shared/components/alert-list/alert-list.component';
import {FormsModule} from "@angular/forms";
import {AddPostComponent} from "./modules/posts/components/add-post/add-post.component";
import {PostsModule} from "./modules/posts/posts.module";
import { ProfilesModule } from './modules/profiles/profiles.module';

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
    PostsModule,
    ProfilesModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
