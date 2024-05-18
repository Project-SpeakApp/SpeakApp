import {Component, computed, OnInit} from '@angular/core';
import {ChatService} from "../../services/chat.service";
import {ChatPreviewDTO} from "../../../../shared/types/chat/chat-preview-dto.model";
import {AuthService} from "../../../../shared/services/auth.service";
import {MessageGetDTO} from "../../../../shared/types/chat/message-get-DTO.model";
import {MessagePrivateCreateDTO} from "../../../../shared/types/chat/message-private-create-dto.model";

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.css']
})
export class MainViewComponent implements OnInit{
  chatPreviews: ChatPreviewDTO[] = [];
  selectedChat: ChatPreviewDTO | null = null;
  currentPage: number = 0;
  totalPages: number = 0;
  userId: string = '6c84fb95-12c4-11ec-82a8-0242ac130003';
  messages: MessageGetDTO [] = [];
  messageContent: string = '';

  ngOnInit(): void {
    this.loadChatPreviews();
    this.chatService.connect(this.userId)
  }

  constructor(private chatService: ChatService, private authService: AuthService) { }


  loadChatPreviews(): void {
    this.chatService.getChatPreview(0,10).subscribe(
      (response) => {
        console.log(response);
        this.chatPreviews = response.chatPreviewDTOS;
        this.totalPages = response.totalPages;
        this.currentPage = response.currentPage;
      },
      (error) => {
        console.error('Failed to load chat previews:', error);
      }
    );
  }

  selectChat(chatPreview: ChatPreviewDTO): void {
    this.selectedChat = chatPreview;
    this.chatService.getMessages(this.selectedChat.conversationGetDTO.conversationId, 0,10).subscribe(
      (response) => {
        console.log(response);
        this.messages = response;
      },
      (error) => {
        console.error('Failed to load chat messages: ', error);
      }
    );
  }

  sendMessage(): void {
    if (!this.messageContent.trim()) {
      return;
    }

    if (this.selectedChat) {
      const messageDTO: MessagePrivateCreateDTO = {
        fromUserId: this.userId,
        toUserId: this.selectedChat.conversationGetDTO.otherUserId,
        content: this.messageContent,
        type: 'TEXT',
        conversationId: this.selectedChat.conversationGetDTO.conversationId
      };

      this.chatService.sendMessage(messageDTO).subscribe(
        (response) => {
          console.log('Message sent successfully', response);
          this.messages.unshift(response);
          this.messageContent = '';
        },
        (error) => {
          console.error('Failed to send message:', error);
        }
      );
    }
  }


}
