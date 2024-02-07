import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {DateFormatting} from "../../../../shared/util/DateFormatting";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
})
export class CommentComponent implements OnInit{
  @Input() comment: CommentGetModel = {} as CommentGetModel;
  @Output() deleted: EventEmitter<string> = new EventEmitter<string>();


  formattedDate: string = '';

  handleDeletion(commentId?: string): void {
    if(commentId) {
      this.deleted.emit(commentId);
    }
  }

  ngOnInit(): void {
    this.formattedDate = DateFormatting.formatDateTime(this.comment.createdAt);
  }

}
