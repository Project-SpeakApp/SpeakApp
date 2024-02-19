import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CommentService} from "../../sevices/comment.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {SortOrder} from "../../../../shared/types/posts/SortOrder.enum";

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
})
export class CommentListComponent implements OnInit, OnDestroy{
  @Input() postId: string = "";
  comments: CommentGetModel[] = [];
  isLoading: boolean = false;

  numberOfPages: number = 0;

  currentPage: number = 0;

  totalComments: number = 0;

  currentComments: number = 0;

  subscription = new Subscription();

  sortBy: string = "createdAt";
  sortDirection: SortOrder = SortOrder.DESC;


  constructor(private commentService: CommentService, private auth: AuthService) {
  }

  handleDeletion(commentToDelete: string) {
    if (commentToDelete) {
      this.comments = this.comments.filter((comment) => comment.commentId !== commentToDelete);
      this.totalComments--;
      this.currentComments--;
    }
  }
  getComments(pageSize: number, pageNumber: number): void {
    this.isLoading = true;
    this.subscription = this.commentService.getComments(this.postId, this.auth.state().userId, pageNumber, pageSize, this.sortBy, this.sortDirection).subscribe({
      next: (response) => {
        if(this.currentPage == 0 && pageSize != 2) {
          this.comments = response.commentGetDTOS;
        }
        else this.comments = [...this.comments, ...response.commentGetDTOS];
        if(pageSize != 2) this.currentPage = response.currentPage+1;
        this.numberOfPages = response.totalPages;
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
    this.getComments(this.currentComments, 0);
  }

  addComment(newComment?: CommentGetModel): void {
    if(newComment) {
      this.comments.unshift(newComment);
      this.currentComments += 1;
      this.totalComments += 1;
    }
  }

  ngOnInit(): void {
    this.getComments(2, 0);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


}
