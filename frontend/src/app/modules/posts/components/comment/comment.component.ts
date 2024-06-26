import {Component, EventEmitter, Input, OnInit, OnDestroy, Output} from '@angular/core';
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {DateFormatting} from "../../../../shared/util/DateFormatting";
import {ReactionType} from "../../../../shared/types/posts/ReactionType.enum";
import {ReactionsGet} from "../../../../shared/types/posts/reactions-get.model";
import {ReactionService} from "../../sevices/reaction.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
})
export class CommentComponent implements OnInit, OnDestroy{
  @Input() comment: CommentGetModel = {} as CommentGetModel;
  @Output() deleted: EventEmitter<string> = new EventEmitter<string>();


  formattedDate: string = '';
  reactions: ReactionsGet = {} as ReactionsGet;
  sortedReactions: [ReactionType, number][] = [];
  postReactionTypesCount = 0;
  currentUserReactionType: ReactionType | null = null;
  subscription = new Subscription();
  isEdited: boolean = false;
  userId: string = '';
  formattedDateOfEditing: string = '';

  constructor(
    private reactionService: ReactionService,
    private authService: AuthService,
    private router: Router) {}

  upsertReaction(reactionType: ReactionType) {
    if (this.reactionService.isLoading()) return;
    this.subscription = this.reactionService
      .upsertReaction(this.comment.commentId, reactionType, this.comment.currentUserReactionType, 'comment')
      .subscribe(() => {
        this.changeReaction(reactionType === this.comment.currentUserReactionType ? null : reactionType)
        this.ngOnInit();
      });
  }

  changeReaction(newReactionType: ReactionType | null): void {
    const oldReaction = this.comment.currentUserReactionType;
    this.comment.currentUserReactionType = newReactionType;

    // update reaction type count
    if (oldReaction !== null) {
      this.comment.reactionsGetDTO.sumOfReactionsByType.set(
        oldReaction,
        this.comment.reactionsGetDTO.sumOfReactionsByType.get(oldReaction)! - 1,
      );
    }
    if (newReactionType !== null) {
      const currentCount = this.comment.reactionsGetDTO.sumOfReactionsByType.get(newReactionType);
      if (currentCount) {
        this.comment.reactionsGetDTO.sumOfReactionsByType.set(newReactionType, currentCount + 1);
      } else {
        this.comment.reactionsGetDTO.sumOfReactionsByType.set(newReactionType, 1);
      }
    }

    // update overall count
    if (oldReaction === null && newReactionType !== null) {
      this.comment.reactionsGetDTO.sumOfReactions++;
    } else if (oldReaction !== null && newReactionType === null) {
      this.comment.reactionsGetDTO.sumOfReactions--;
    }

    // rerender component
    this.comment = {...this.comment};
  }

  handleDeletion(commentId?: string): void {
    if(commentId) {
      this.deleted.emit(commentId);
    }
  }

  enableEditing(): void {
    this.isEdited = !this.isEdited;
  }

  updateComment(updatedComment?: CommentGetModel) : void {
    this.isEdited = false;
    if(updatedComment) {
      this.comment = updatedComment;
      if(this.comment.modifiedAt) this.formattedDateOfEditing = DateFormatting.formatDateTime(this.comment.modifiedAt);
    }
  }

  async redirectToProfile() {
    await this.router.navigate(['/profiles', this.comment.author.userId, 'info']);
  }

  ngOnInit(): void {
    this.formattedDate = DateFormatting.formatDateTime(this.comment.createdAt);
    if(this.comment.modifiedAt) this.formattedDateOfEditing = DateFormatting.formatDateTime(this.comment.modifiedAt);
    this.reactions = this.comment.reactionsGetDTO;
    this.currentUserReactionType = this.comment.currentUserReactionType;
    this.postReactionTypesCount = this.comment.reactionsGetDTO.sumOfReactionsByType.size;

    if (this.reactions.sumOfReactionsByType && this.reactions.sumOfReactionsByType.size > 0) {
      this.sortedReactions = [...this.reactions.sumOfReactionsByType.entries()]
        .filter((reaction) => reaction[1] > 0)
        .sort((a, b) => b[1] - a[1]);
    }
    else {
      this.sortedReactions = [];
    }
    this.userId = this.authService.state().userId;

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
