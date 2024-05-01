import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {CommentService} from "../../sevices/comment.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {ReactionType} from "../../../../shared/types/posts/ReactionType.enum";
import {SortOrder} from "../../../../shared/types/posts/SortOrder.enum";


@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
})
export class CommentListComponent implements OnInit, OnDestroy{
  @Input() postId: string = "";
  @Output() contentAdded: EventEmitter<any> = new EventEmitter<any>();
  @Output() contentDeleted: EventEmitter<any> = new EventEmitter<any>();
  comments: CommentGetModel[] = [];
  isLoading: boolean = false;

  totalComments: number = 0;

  currentComments: number = 0;

  subscription = new Subscription();

  constructor(private commentService: CommentService) {
  }

  private parseComments(comments: CommentGetModel[]) {
    comments.forEach((comment) => {
      const keys = Object.keys(comment.reactionsGetDTO.sumOfReactionsByType);
      const value = Object.values(comment.reactionsGetDTO.sumOfReactionsByType);

      comment.reactionsGetDTO.sumOfReactionsByType = new Map();

      for (let i = 0; i < keys.length; i++) {
        const reactionType: ReactionType = ReactionType[keys[i] as keyof typeof ReactionType];
        comment.reactionsGetDTO.sumOfReactionsByType.set(reactionType, value[i]);
      }
    })
  }

  sortBy: string = "createdAt";
  sortDirection: SortOrder = SortOrder.DESC;

  handleDeletion(commentToDelete: string) {
    if (commentToDelete) {
      this.comments = this.comments.filter((comment) => comment.commentId !== commentToDelete);
      this.totalComments--;
      this.currentComments--;
      this.contentDeleted.emit();
    }
  }

  getComments(firstComment: number, numberOfComments: number, sort: boolean): void {
    this.isLoading = true;
    this.subscription = this.commentService.getComments(this.postId, firstComment, firstComment + numberOfComments, this.sortBy, this.sortDirection).subscribe({
      next: (response) => {
        this.comments = [...this.comments, ...response.commentGetDTOS];
        this.parseComments(this.comments);
        this.totalComments = response.totalComments;
        this.isLoading = false;
        this.currentComments  = this.comments.length;
      },
      error: (error) => {
        this.isLoading = false;
        console.log(error);
      }
    });
  }

  handleSorting(sortBy: string, sortOrder: SortOrder) : void {
    if(this.totalComments === 0) return;
    this.comments = [];
    this.totalComments = 0;
    this.sortBy = sortBy;
    this.sortDirection = sortOrder;
    this.getComments(0, this.currentComments, true);
  }

  addComment(newComment?: CommentGetModel): void {
    if(newComment) {
      this.comments.unshift(newComment);
      this.currentComments += 1;
      this.totalComments += 1;
      this.contentAdded.emit();
    }
  }

  ngOnInit(): void {
    this.getComments(0,2, false);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


}
