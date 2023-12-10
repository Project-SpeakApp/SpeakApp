import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserProfilePageComponent } from './components/user-profile-page/user-profile-page.component';
import { UserProfileNavigationComponent } from './components/user-profile-navigation/user-profile-navigation.component';
import { UserProfileNavigationItemComponent } from './components/user-profile-navigation-item/user-profile-navigation-item.component';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { ProfileSearchFormComponent } from './components/profile-search-form/profile-search-form.component';



@NgModule({
  declarations: [
    UserProfilePageComponent,
    UserProfileNavigationComponent,
    UserProfileNavigationItemComponent,
    ProfileSearchFormComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
  ],
})
export class ProfilesModule { }
