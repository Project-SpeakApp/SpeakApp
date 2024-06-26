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
import { UserProfileSettingsPageComponent } from './components/user-profile-settings-page/user-profile-settings-page.component';
import { UserProfileEditFormComponent } from './components/user-profile-edit-form/user-profile-edit-form.component';
import { NgxMatDatetimePickerModule } from '@angular-material-components/datetime-picker';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule} from '@angular/material/select';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserProfileFriendsPageComponent } from './components/user-profile-friends-page/user-profile-friends-page.component';
import { UserFriendsListComponent } from './components/user-friends-list/user-friends-list.component';
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import { FriendCardComponent } from './components/friend-card/friend-card.component';



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
    UserProfileInfoPageComponent,
    UserProfileSettingsPageComponent,
    UserProfileEditFormComponent,
    UserProfileFriendsPageComponent,
    UserFriendsListComponent,
    FriendCardComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    SharedModule,
    NgxMatDatetimePickerModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    FormsModule,
    MatSelectModule,
    ReactiveFormsModule,
    InfiniteScrollModule,
  ],


})
export class ProfilesModule { }
