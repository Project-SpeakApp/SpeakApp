import {Component, Input, OnInit} from '@angular/core';
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {DateFormatting} from "../../../../shared/util/DateFormatting";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit{
  @Input() comment: CommentGetModel = {} as CommentGetModel;

  formattedDate: string = '';

  ngOnInit(): void {
    //this.formattedDate = DateFormatting.formatDateTime(this.comment.createdAt);
  }

}
