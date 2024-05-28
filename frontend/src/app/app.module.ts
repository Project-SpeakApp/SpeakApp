import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './shared/home-page/home-page.component';
import { ProfilesModule } from './modules/profiles/profiles.module';
import {PostsModule} from "./modules/posts/posts.module";
import { NotFoundPageComponent } from './shared/not-found-page/not-found-page.component';
import { SharedModule } from './shared/shared.module';
import {initializeKeycloak} from "./init/keycloak-init.factory";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {HttpErrorHandler} from "./http-interceptor/http-error-handler";
import {AlertService} from "./shared/services/alert.service";
import {ChatModule} from "./modules/chat/chat.module";

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
    PostsModule,
    SharedModule,
    KeycloakAngularModule,
    ChatModule
  ],
  exports: [],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorHandler,
      multi: true,
      deps: [AlertService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
