import {Component, Input, OnInit} from '@angular/core';
import {CommentGet} from "../../../../shared/types/posts/comment-get";
import {DateFormatting} from "../../../../shared/util/DateFormatting";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit{
  @Input() comment: CommentGet = {} as CommentGet;

  formattedDate: string = '';

  ngOnInit(): void {
    this.formattedDate = DateFormatting.formatDateTime(this.comment.createdAt);
  }

}
