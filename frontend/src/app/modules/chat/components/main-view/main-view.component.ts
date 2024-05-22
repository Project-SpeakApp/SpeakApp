import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { ChatPreviewDTO } from '../../../../shared/types/chat/chat-preview-dto.model';
import { AuthService } from '../../../../shared/services/auth.service';
import { MessageGetDTO } from '../../../../shared/types/chat/message-get-DTO.model';
import { MessagePrivateCreateDTO } from '../../../../shared/types/chat/message-private-create-dto.model';
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

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
  private subscriptions: Subscription = new Subscription();

  constructor(private chatService: ChatService, private authService: AuthService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadChatPreviews();
    this.subscriptions.add(
      this.chatService.messageReceived.subscribe((newMessage: MessageGetDTO) => {
        const chatPreviewIndex = this.chatPreviews.findIndex(x => x.conversationGetDTO.conversationId === newMessage.conversationId);
        if (chatPreviewIndex !== -1) {
          this.chatPreviews[chatPreviewIndex].lastMessage = newMessage;
          console.log(newMessage);
          this.cdr.markForCheck();
        }
        if (this.selectedChat && this.selectedChat.conversationGetDTO.conversationId === newMessage.conversationId) {
          this.messages.unshift(newMessage);
          this.cdr.markForCheck();
        }
      })
    );
  }

  ngOnDestroy(): void {
    console.log('Disconnected');
    this.chatService.disconnect();
    this.subscriptions.unsubscribe();
  }

  onScroll(): void {
    console.log('Scroll event detected');
    if (this.isLoading2 || !this.selectedChat) return;

    this.isLoading2 = true;
    this.chatService.getMessages(this.selectedChat.conversationGetDTO.conversationId, this.currentPageMessages, 10).subscribe(
      response => {
        this.messages.push(...response.listOfMessages);
        this.currentPageMessages++;
        console.log('Messages fetched:', response);
        this.isLoading2 = false;
        this.cdr.markForCheck();
      },
      error => {
        console.log('Failed to load more messages');
        this.isLoading2 = false;
      }
    );
  }

  loadChatPreviews(): void {
    this.chatService.getChatPreview(0, 10).subscribe(
      response => {
        console.log(response);
        this.chatPreviews = response.chatPreviewDTOS;
        this.totalPagesPreview = response.totalPages;
        this.currentPagePreview = response.currentPage + 1;
        this.cdr.markForCheck();
      },
      error => {
        console.error('Failed to load chat previews:', error);
      }
    );
  }

  selectChat(chatPreview: ChatPreviewDTO): void {
    this.isLoading2 = true;
    this.chatService.connect(this.state().userId);
    this.selectedChat = chatPreview;
    this.chatService.getMessages(this.selectedChat.conversationGetDTO.conversationId, 0, 10).subscribe(
      response => {
        console.log(response);
        this.messages = response.listOfMessages;
        this.currentPageMessages = 1;
        this.isLoading2 = false;
        this.cdr.markForCheck();
      },
      error => {
        console.error('Failed to load chat messages:', error);
        this.isLoading2 = false;
      }
    );
  }

  sendMessage(): void {
    this.isLoading = true;

    if (this.contentControl.invalid) {
      console.log('Invalid input');
      this.isLoading = false;
      return;
    }

    if (this.selectedChat) {
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
          this.messages.unshift(response); // Assuming the response contains the new message
          this.contentControl.reset();
          this.cdr.markForCheck();
        },
        error => {
          console.error('Failed to send message:', error);
        }
      );
    }

    this.isLoading = false;
  }
}
