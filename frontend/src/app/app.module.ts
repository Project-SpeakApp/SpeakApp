import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './shared/home-page/home-page.component';
import { ProfilesModule } from './modules/profiles/profiles.module';
import {FormsModule} from "@angular/forms";
import {PostsModule} from "./modules/posts/posts.module";
import { NotFoundPageComponent } from './shared/not-found-page/not-found-page.component';
import { SharedModule } from './shared/shared.module';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    NotFoundPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ProfilesModule,
    FormsModule,
    PostsModule,
    SharedModule
  ],
  exports: [],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
