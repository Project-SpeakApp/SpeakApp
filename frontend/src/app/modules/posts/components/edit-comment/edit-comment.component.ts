import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AlertService} from "../../../../shared/services/alert.service";
import {AuthService} from "../../../../shared/services/auth.service";
import {UpdateCommentModel} from "../../../../shared/types/posts/update-comment.model";
import {Subscription} from "rxjs";
import {CommentService} from "../../sevices/comment.service";
import {CalculateNumberOfRows} from "../../../../shared/util/CalculateNumberOfRows";

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['../../css-files/edit-style-sheet.component.css']
})
export class EditCommentComponent implements OnInit, OnDestroy{
  @Input() currentContent: string = "";

  @Input() commentId: string = "";

  @Input() authorId: string = "";

  @Output() contentUpdated: EventEmitter<CommentGetModel> = new EventEmitter<CommentGetModel>();
  initContent: string = ""
  model: UpdateCommentModel;
  numberOfRows: number = 0;

  visible: boolean = false;
  isLoading: boolean = false;
  myForm!: FormGroup;
  private UpdateCommentSubscription?: Subscription;

  constructor(private formBuilder: FormBuilder, private alertService: AlertService, private commentService: CommentService, private authService: AuthService) {
    this.model = {
      content: ''
    };
  }
  onFormSubmit(): void {
    if (this.myForm.valid && this.initContent !== this.myForm.value.commentContent) {
      this.isLoading = true;
      this.currentContent = this.myForm.value.commentContent;
      this.model.content = this.currentContent;
      this.UpdateCommentSubscription = this.commentService.updateComment(this.model, this.authService.state().userId, this.commentId).subscribe(
        (updatedPost) => { this.alertService.showAlert('Comment updated successfully', 'success'), this.contentUpdated.emit(updatedPost), this.isLoading = false;},
        (error) => {this.isLoading =false;}

      );
    } else if (this.initContent === this.myForm.value.commentContent) {
      this.closeForm();
    } else {
      this.alertService.showAlert('Type content', 'error');
    }
  }

  closeForm() {
    this.contentUpdated.emit();
  }

  ngOnInit(): void {
    this.initContent = this.currentContent;
    if(this.authorId === this.authService.state().userId) this.visible = true;
    this.myForm = this.formBuilder.group({
      commentContent: [this.currentContent, [Validators.required, Validators.minLength(1)]]
    });
    this.numberOfRows = CalculateNumberOfRows.calculateNumberOfRows(this.currentContent);
  }
  ngOnDestroy(): void {
    this.UpdateCommentSubscription?.unsubscribe();
  }

}
