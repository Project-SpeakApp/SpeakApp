import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { AlertListComponent } from './components/alert-list/alert-list.component';
import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UserAvatarComponent } from './components/user-avatar/user-avatar.component';



@NgModule({
  declarations: [
    HeaderComponent,
    AlertListComponent,
    LoadingSpinnerComponent,
    UserAvatarComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ],
  exports: [
    HeaderComponent,
    AlertListComponent,
    LoadingSpinnerComponent,
    UserAvatarComponent,
  ],
})
export class SharedModule { }
