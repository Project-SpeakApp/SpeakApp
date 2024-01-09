import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PostService} from "../../sevices/post.service";
import {AuthService} from "../../../../shared/services/auth.service";
import {AddComment} from "../../../../shared/types/posts/add-comment.model";
import {AlertService} from "../../../../shared/services/alert.service";
import {Subscription} from "rxjs";
import {CommentService} from "../../sevices/comment.service";
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.css']
})
export class AddCommentComponent implements OnInit, OnDestroy{

  @Input() postId: string = "";

  @Output() contentAdded: EventEmitter<CommentGetModel> = new EventEmitter<CommentGetModel>();

  isLoading: boolean = false;
    model: AddComment;
    private addCommentSubscription?: Subscription;

  myForm!: FormGroup;
    constructor(private formBuilder: FormBuilder, private commentService: CommentService,
                private authService: AuthService, private alertService: AlertService) {
      this.model = {
        content: '',
        postId: this.postId,
      };
    }

  onFormSubmit(): void {
      this.isLoading = true;
      if(this.myForm.valid) {
        this.model.content = this.myForm.value.content;
        this.addCommentSubscription = this.commentService.addComment(this.model, this.authService.state().userId).subscribe(
          (newComment) => this.contentAdded.emit(newComment)
        );
      } else {
        this.alertService.showAlert('Type Comment', 'error');
      }
      this.isLoading = false;

  }

  ngOnInit(): void {
      this.myForm = this.formBuilder.group({
        content: ['', [Validators.required, Validators.minLength(1)]]
      });
  }

  ngOnDestroy(): void {
      this.addCommentSubscription?.unsubscribe();
  }


}
