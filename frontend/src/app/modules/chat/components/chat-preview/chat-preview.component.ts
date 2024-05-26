import {Component, Input, OnInit} from '@angular/core';
import {ChatPreviewDTO} from "../../../../shared/types/chat/chat-preview-dto.model";
import {ChatPreviewTextFormatting} from "../../../../shared/util/ChatPreviewTextFormatting";

@Component({
  selector: 'app-chat-preview',
  templateUrl: './chat-preview.component.html',
  styleUrls: ['./chat-preview.component.css']
})
export class ChatPreviewComponent implements OnInit {
  ngOnInit(): void {
      //Just to have better web design
      this.chatPreview.lastMessage.fromUser.profilePhotoId = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
  }
  @Input() chatPreview!: ChatPreviewDTO;
  @Input() userId: string = "";

  protected readonly ChatPreviewTextFormatting = ChatPreviewTextFormatting;
}
