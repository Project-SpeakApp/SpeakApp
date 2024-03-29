import {Component, EventEmitter, Input, OnDestroy, Output} from '@angular/core';
import { FormControl, Validators} from "@angular/forms";
import {AuthService} from "../../../../shared/services/auth.service";
import {AddComment} from "../../../../shared/types/posts/add-comment.model";
import {Subscription} from "rxjs";
import {CommentService} from "../../sevices/comment.service";
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.css']
})
export class AddCommentComponent implements OnDestroy{

  @Input() postId: string = "";

  @Output() contentAdded: EventEmitter<CommentGetModel> = new EventEmitter<CommentGetModel>();

  contentControl = new FormControl('', [Validators.required, Validators.minLength(1)]);


  isLoading: boolean = false;
    model: AddComment;
    private addCommentSubscription?: Subscription;

    constructor(private commentService: CommentService) {
      this.model = {
        content: '',
        postId: this.postId,
      };
    }

  onFormSubmit(): void {
      if(this.contentControl.valid && this.contentControl.value != null) {
        this.isLoading = true;
        this.model.content = this.contentControl.value;
        this.model.postId = this.postId;
        this.addCommentSubscription = this.commentService.addComment(this.model).subscribe(
          (newComment) => {
            this.contentAdded.emit(newComment);
            this.contentControl.reset();
            this.isLoading = false;
          },
          (error) => {this.isLoading= false}
        );
      }

  }

  ngOnDestroy(): void {
      this.addCommentSubscription?.unsubscribe();
  }


}
