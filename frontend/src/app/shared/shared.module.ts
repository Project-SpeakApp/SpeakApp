import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { AlertListComponent } from './components/alert-list/alert-list.component';
import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    HeaderComponent,
    AlertListComponent,
    LoadingSpinnerComponent,
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
  ],
})
export class SharedModule { }
