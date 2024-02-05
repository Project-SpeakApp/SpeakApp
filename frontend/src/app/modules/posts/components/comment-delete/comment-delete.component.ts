import {Component, EventEmitter, Input, OnDestroy, OnInit, Output, signal} from '@angular/core';
import {AlertService} from "../../../../shared/services/alert.service";
import {AuthService} from "../../../../shared/services/auth.service";
import {Subscription} from "rxjs";
import {CommentService} from "../../sevices/comment.service";

@Component({
  selector: 'app-comment-delete',
  templateUrl: './comment-delete.component.html'
})
export class CommentDeleteComponent implements OnDestroy, OnInit{

  @Input() authorId: string = '';

  @Input() commentId: string = '';
  @Output() deleted: EventEmitter<string> = new EventEmitter<string>();

  visible: boolean = false;
  isLoading: boolean = false;
  private deleteCommentSubscription?: Subscription;

  constructor(private alertService: AlertService, private authService: AuthService, private commentService: CommentService) {
  }

  onFormSubmit(modalId: string): void {
    this.isLoading = true;
    this.deleteCommentSubscription = this.commentService.deleteComment( this.commentId, this.authService.state().userId).subscribe(
      () => {
        this.deleted.emit(this.commentId);
        this.closeModal(modalId);
        this.alertService.showAlert("Comment deleted successfully", 'success');
        this.isLoading = false;

      }
    );
  }

  openModal(modalId: string): void {
    const modal = document.getElementById(modalId) as HTMLDialogElement;
    if (modal) {
      modal.showModal();
    }
  }

  closeModal(modalId: string): void {
    const modal = document.getElementById(modalId) as HTMLDialogElement;
    if (modal) {
      modal.close();
    }
  }
  ngOnDestroy(): void {
    this.deleteCommentSubscription?.unsubscribe();
  }

  ngOnInit(): void {
    if(this.authService.state().userId === this.authorId) {
      this.visible = true;
    }
  }

}
