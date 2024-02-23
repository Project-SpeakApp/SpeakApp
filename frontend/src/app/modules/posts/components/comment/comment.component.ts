import {Component, Input, OnInit} from '@angular/core';
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {DateFormatting} from "../../../../shared/util/DateFormatting";
import {ReactionType} from "../../../../shared/types/posts/ReactionType.enum";
import {ReactionsGet} from "../../../../shared/types/posts/reactions-get.model";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
})
export class CommentComponent implements OnInit{
  @Input() comment: CommentGetModel = {} as CommentGetModel;

  formattedDate: string = '';

  reactions: ReactionsGet = {} as ReactionsGet;
  sortedReactions: [ReactionType, number][] = [];

  postReactionTypesCount = 0;

  ngOnInit(): void {
    this.formattedDate = DateFormatting.formatDateTime(this.comment.createdAt);
    this.reactions = this.comment.reactionsGetDTO;
    this.postReactionTypesCount = this.comment.reactionsGetDTO.sumOfReactionsByType.size;
    if (this.reactions.sumOfReactionsByType && this.reactions.sumOfReactionsByType.size > 0) {
      this.sortedReactions = [...this.reactions.sumOfReactionsByType.entries()]
        .filter((reaction) => reaction[1] > 0)
        .sort((a, b) => b[1] - a[1]);
    }
    else {
      this.sortedReactions = [];
    }
  }

}
