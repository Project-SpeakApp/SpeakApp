import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainViewComponent } from './components/main-view/main-view.component';
import { ChatPreviewComponent } from './components/chat-preview/chat-preview.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {SharedModule} from "../../shared/shared.module";



@NgModule({
  declarations: [
    MainViewComponent,
    ChatPreviewComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    InfiniteScrollModule,
    SharedModule
  ]
})
export class ChatModule { }
