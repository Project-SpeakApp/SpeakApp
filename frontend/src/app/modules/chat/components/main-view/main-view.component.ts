import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChatService} from "../../services/chat.service";
import {ChatPreviewDTO} from "../../../../shared/types/chat/chat-preview-dto.model";
import {AuthService} from "../../../../shared/services/auth.service";
import {MessageGetDTO} from "../../../../shared/types/chat/message-get-DTO.model";
import {MessagePrivateCreateDTO} from "../../../../shared/types/chat/message-private-create-dto.model";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.css']
})
export class MainViewComponent implements OnInit, OnDestroy {
  chatPreviews: ChatPreviewDTO[] = [];
  selectedChat: ChatPreviewDTO | null = null;
  currentPagePreview: number = 0;
  totalPagesPreview: number = 0;
  currentPageMessages: number = 0;
  state = this.authService.state;
  messages: MessageGetDTO[] = [];
  isLoading: boolean = false;
  contentControl = new FormControl('', [Validators.required, Validators.minLength(1)]);
  isLoading2: boolean = false;

  constructor(private chatService: ChatService, private authService: AuthService) { }

  ngOnInit(): void {
    this.loadChatPreviews();
    this.chatService.messageReceived.subscribe((newMessage: MessageGetDTO) => {
      this.messages.unshift(newMessage);
    });
  }

  ngOnDestroy(): void {
    console.log('Disconnected');
    this.chatService.disconnect();
  }

  onScroll(): void {
    console.log('Scroll event detected');
    if(this.isLoading2) return;
    if (this.selectedChat) {
      this.isLoading2 = true;
      this.chatService.getMessages(this.selectedChat.conversationGetDTO.conversationId, this.currentPageMessages, 10).subscribe(
        (response) => {
          this.messages.push(...response);
          this.currentPageMessages++;
          console.log('Messages fetched:', response);
          this.isLoading2 = false;
        },
        (error) => {
          console.log('Failed to load more messages');
          this.isLoading2 = false;
        }
      );
    }
  }

  loadChatPreviews(): void {
    this.chatService.getChatPreview(0, 10).subscribe(
      (response) => {
        console.log(response);
        this.chatPreviews = response.chatPreviewDTOS;
        this.totalPagesPreview = response.totalPages;
        this.currentPagePreview = response.currentPage + 1;
      },
      (error) => {
        console.error('Failed to load chat previews:', error);
      }
    );
  }

  selectChat(chatPreview: ChatPreviewDTO): void {
    this.isLoading2 = true;
    this.chatService.connect(this.state().userId);
    this.selectedChat = chatPreview;
    this.chatService.getMessages(this.selectedChat.conversationGetDTO.conversationId, 0, 15).subscribe(
      (response) => {
        console.log(response);
        this.messages = response;
        this.currentPageMessages = 1;
        this.isLoading2 = false;
      },
      (error) => {
        console.error('Failed to load chat messages:', error);
        this.isLoading2 = false;
      }
    );
  }

  sendMessage(): void {
    this.isLoading = true;
    console.log(this.contentControl.value);

    if (this.contentControl.invalid) {
      console.log('Invalid input');
      this.isLoading = false;
      return;
    }

    if (this.selectedChat) {
      console.log(this.contentControl.value);
      const messageDTO: MessagePrivateCreateDTO = {
        fromUserId: this.state().userId,
        toUserId: '6c84fb96-12c4-11ec-82a8-0242ac130003',
        content: this.contentControl.value as string,
        type: 'TEXT',
        conversationId: this.selectedChat.conversationGetDTO.conversationId
      };

      this.chatService.sendMessage(messageDTO).subscribe(
        response => {
          console.log('Message sent successfully', response);
          this.messages.unshift()
          this.contentControl.reset();
        },
        error => {
          console.error('Failed to send message:', error);
        }
      );
    }

    this.isLoading = false;
  }
}
