import { ChangeDetectorRef, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ChatPreviewDTO } from '../../../../shared/types/chat/chat-preview-dto.model';
import { ChatPreviewTextFormatting } from '../../../../shared/util/ChatPreviewTextFormatting';

@Component({
  selector: 'app-chat-preview',
  templateUrl: './chat-preview.component.html',
  styleUrls: ['./chat-preview.component.css']
})
export class ChatPreviewComponent implements OnInit, OnDestroy {
  @Input() chatPreview!: ChatPreviewDTO;
  @Input() userId: string = "";
  private intervalId: any;

  protected readonly ChatPreviewTextFormatting = ChatPreviewTextFormatting;

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    // Just to have better web design
    this.chatPreview.lastMessage.fromUser.profilePhotoId = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";

    if (!(this.chatPreview.lastMessage.sentAt instanceof Date)) {
      this.chatPreview.lastMessage.sentAt = new Date(this.chatPreview.lastMessage.sentAt);
    }

    this.intervalId = setInterval(() => {
      this.updateTime();
    }, 60000);
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  updateTime() {
    this.cdr.markForCheck();
  }
}
