import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserProfilePageComponent } from './components/user-profile-page/user-profile-page.component';
import { UserProfileNavigationComponent } from './components/user-profile-navigation/user-profile-navigation.component';
import { UserProfileNavigationItemComponent } from './components/user-profile-navigation-item/user-profile-navigation-item.component';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { ProfileSearchFormComponent } from './components/profile-search-form/profile-search-form.component';
import { UserProfileInfoComponent } from './components/user-profile-info/user-profile-info.component';
import { UserProfileInfoLabelComponent } from './components/user-profile-info-label/user-profile-info-label.component';
import { UserProfileInfoBirthdayCardComponent } from './components/user-profile-info-birthday-card/user-profile-info-birthday-card.component';
import { ProfileComponent } from './components/profile/profile.component';
import { HttpClientModule } from '@angular/common/http';
import { UserProfileInfoPageComponent } from './components/user-profile-info-page/user-profile-info-page.component';
import { SharedModule } from 'src/app/shared/shared.module';



@NgModule({
  declarations: [
    UserProfilePageComponent,
    UserProfileNavigationComponent,
    UserProfileNavigationItemComponent,
    ProfileSearchFormComponent,
    UserProfileInfoComponent,
    UserProfileInfoLabelComponent,
    UserProfileInfoBirthdayCardComponent,
    ProfileComponent,
    UserProfileInfoPageComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    SharedModule
  ],


})
export class ProfilesModule { }
