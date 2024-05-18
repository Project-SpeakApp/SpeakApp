import {Component, Input, OnInit} from '@angular/core';
import {ChatPreviewDTO} from "../../../../shared/types/chat/chat-preview-dto.model";

@Component({
  selector: 'app-chat-preview',
  templateUrl: './chat-preview.component.html',
  styleUrls: ['./chat-preview.component.css']
})
export class ChatPreviewComponent implements OnInit {
  ngOnInit(): void {
      this.chatPreview.lastMessage.fromUser.profilePhotoId = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
  }
  @Input() chatPreview!: ChatPreviewDTO;
  @Input() userId: string = "";

  getInitials(name: string): string {
    const initials = name.split(' ').map(n => n[0]).join('');
    return initials.toUpperCase();
  }
}
