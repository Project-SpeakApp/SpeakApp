import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainViewComponent } from './components/main-view/main-view.component';
import { ConversationSummaryComponent } from './components/conversation-summary/conversation-summary.component';
import { ChatPreviewComponent } from './components/chat-preview/chat-preview.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {SharedModule} from "../../shared/shared.module";



@NgModule({
  declarations: [
    MainViewComponent,
    ConversationSummaryComponent,
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
