import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {PostService} from "../../sevices/post.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";
import {AlertService} from "../../../../shared/services/alert.service";

@Component({
  selector: 'app-delete-post',
  templateUrl: './delete-post.component.html',
  styleUrls: ['./delete-post.component.css']
})
export class DeletePostComponent implements OnDestroy, OnInit{

  @Input() postId: string = "";
  @Input() authorId: string = "";
  @Output() deleted: EventEmitter<string> = new EventEmitter<string>();

  visible: boolean = false;
  private addPostSubscription?: Subscription;


  isLoading: boolean = false;
  constructor(private alertService: AlertService, private postService: PostService, private authService: AuthService) {
  }



  openModal(modalId: string): void {
    const modal = document.getElementById(modalId) as HTMLDialogElement;
    if (modal) {
      modal.showModal();
    }
  }


  onFormSubmit(modalId: string): void {
    this.isLoading = true;
    this.addPostSubscription = this.postService.deletePost( this.postId, this.authService.state().userId).subscribe(
      () => {
        this.deleted.emit(this.postId);
        this.closeModal(modalId);
        this.isLoading = false;
        this.alertService.showAlert("Post deleted successfully", 'success');
      }
    );
  }

  closeModal(modalId: string): void {
    const modal = document.getElementById(modalId) as HTMLDialogElement;
    if (modal) {
      modal.close();
    }
  }

  ngOnInit() {
    if (this.authService.state().userId === this.authorId) {
      this.visible = true;
    }
  }
  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }
}
