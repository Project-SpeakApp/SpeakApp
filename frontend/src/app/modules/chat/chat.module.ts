import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainViewComponent } from './components/main-view/main-view.component';
import { ConversationSummaryComponent } from './components/conversation-summary/conversation-summary.component';
import { ConversationDetailsComponent } from './components/conversation-details/conversation-details.component';



@NgModule({
  declarations: [
    MainViewComponent,
    ConversationSummaryComponent,
    ConversationDetailsComponent
  ],
  imports: [
    CommonModule
  ]
})
export class ChatModule { }
