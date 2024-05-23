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
  styleUrls: ['./main-view.component.css'],
})
export class MainViewComponent implements OnInit, OnDestroy {
  chatPreviews: ChatPreviewDTO[] = [];
  selectedChat: ChatPreviewDTO | null = null;
  currentPagePreview: number = 0;
  totalPagesPreview: number = 0;
  currentPageMessages: number = 0;
  messages: MessageGetDTO[] = [];
  isLoading: boolean = false;
  contentControl = new FormControl('', [Validators.required, Validators.minLength(1)]);
  isLoading2: boolean = false;
  private subscriptions: Subscription = new Subscription();
  state = this.authService.state;


  constructor(
    private chatService: ChatService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadChatPreviews();
    this.subscriptions.add(
      this.chatService.messageReceived.subscribe((newMessage: MessageGetDTO) => {
        console.log(newMessage);
        const chatPreviewIndex = this.chatPreviews.findIndex(
          x => x.conversationGetDTO.conversationId === newMessage.conversationId
        );
        if (chatPreviewIndex !== -1) {
          this.chatPreviews[chatPreviewIndex].lastMessage = newMessage;
        }
        if (this.selectedChat && this.selectedChat.conversationGetDTO.conversationId === newMessage.conversationId) {
          console.log(newMessage);
          this.messages = [...this.messages, newMessage];
        }
        this.cdr.detectChanges();
      })
    );
  }

  ngOnDestroy(): void {
    this.chatService.disconnect();
    this.subscriptions.unsubscribe();
  }

  onScroll(): void {
    if (this.isLoading2 || !this.selectedChat) return;
    this.isLoading2 = true;
    this.chatService.getMessages(this.selectedChat.conversationGetDTO.conversationId, this.currentPageMessages, 10).subscribe(
      response => {
        this.messages.push(...response.listOfMessages);
        this.currentPageMessages++;
        this.isLoading2 = false;
      },
      error => {
        console.error('Failed to load more messages:', error);
        this.isLoading2 = false;
      }
    );
  }

  loadChatPreviews(): void {
    this.chatService.getChatPreview(0, 10).subscribe(
      response => {
        this.chatPreviews = response.chatPreviewDTOS;
        this.totalPagesPreview = response.totalPages;
        this.currentPagePreview = response.currentPage + 1;
      },
      error => {
        console.error('Failed to load chat previews:', error);
      }
    );
  }

  selectChat(chatPreview: ChatPreviewDTO): void {
    this.isLoading2 = true;
    this.chatService.connect(this.authService.state().userId); // Ensure correct userId is used
    this.selectedChat = chatPreview;
    this.chatService.getMessages(this.selectedChat.conversationGetDTO.conversationId, 0, 10).subscribe(
      response => {
        this.messages = response.listOfMessages;
        this.currentPageMessages = 1;
        this.isLoading2 = false;
      },
      error => {
        console.error('Failed to load chat messages:', error);
        this.isLoading2 = false;
      }
    );
  }

  sendMessage(): void {
    if (this.contentControl.invalid) return;

    if (this.selectedChat) {
      const userId = this.authService.state().userId;
      if (!userId) {
        console.error('User ID is not available');
        return;
      }
      const messageDTO: MessagePrivateCreateDTO = {
        fromUserId: userId,
        toUserId: this.selectedChat.conversationGetDTO.conversationId,
        content: this.contentControl.value as string,
        type: 'TEXT',
        conversationId: this.selectedChat.conversationGetDTO.conversationId
      };

      this.isLoading = true;
      this.chatService.sendMessage(messageDTO).subscribe(
        response => {
          this.messages.unshift(response);
          this.contentControl.reset();
          this.isLoading = false;
        },
        error => {
          console.error('Failed to send message:', error);
          this.isLoading = false;
        }
      );
    }
  }
}
