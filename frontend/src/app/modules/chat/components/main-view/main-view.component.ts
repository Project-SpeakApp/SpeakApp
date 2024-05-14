import {Component, OnInit} from '@angular/core';
import {ChatService} from "../../services/chat.service";
import {ChatPreview} from "../../../../shared/types/chat/chat-preview.model";

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.css']
})
export class MainViewComponent implements OnInit{
  chatPreviews: ChatPreview[] = [];
  currentPage: number = 0;
  totalPages: number = 0;
  totalItems: number = 0;
  ngOnInit(): void {
    this.loadChatPreviews();
  }

  constructor(private chatService: ChatService) { }


  loadChatPreviews(): void {
    this.chatService.getChatPreview(0,10).subscribe(
      (response) => {
        console.log(response);
        this.chatPreviews = response.chatPreviews;
        this.totalPages = response.totalPages;
        this.totalItems = response.totalItems;
      },
      (error) => {
        console.error('Failed to load chat previews:', error);
      }
    );
  }

}
