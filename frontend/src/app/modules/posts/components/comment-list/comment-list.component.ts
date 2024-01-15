import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CommentService} from "../../sevices/comment.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {ReactionType} from "../../../../shared/types/posts/ReactionType.enum";
import {CommentGetListModel} from "../../../../shared/types/posts/comment-get-list.model";

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

  subscription = new Subscription();


  constructor(private commentService: CommentService, private auth: AuthService) {
  }


  getComments(pageSize: number): void {
    this.isLoading = true;
    this.subscription = this.commentService.getComments(this.postId, this.auth.state().userId, this.currentPage, pageSize).subscribe({
      next: (response) => {
        if(this.currentPage == 0 && pageSize != 2) {
          this.comments = response.commentGetDTOS;
        }
        else this.comments = [...this.comments, ...response.commentGetDTOS];
        if(pageSize != 2) this.currentPage = response.currentPage+1;
        this.numberOfPages = response.totalPages;
        this.isLoading = false;

      },
      error: (error) => {
        this.isLoading = false;

        console.log(error);
      }
    });

  }

  ngOnInit(): void {
    this.getComments(2);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


}