import {Component, Input, OnDestroy, OnInit} from '@angular/core';
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
  private addPostSubscription?: Subscription;

  @Input() postId: string = "";
  @Input() authorId: string = "";


  visible: boolean = false;

  isLoading = this.postService.isLoadingDelete;
  constructor(private alertService: AlertService, private postService: PostService, private authService: AuthService) {
  }

  ngOnInit() {
    if (this.authService.state().userId === this.authorId) {
      this.visible = true;
    }
  }

  openModal(modalId: string): void {
    const modal = document.getElementById(modalId) as HTMLDialogElement;
    if (modal) {
      modal.showModal();
    }
  }


  onFormSubmit(modalId: string): void {
    this.addPostSubscription = this.postService.deletePost( this.postId, this.authService.state().userId).subscribe(
      () => {
        this.closeModal(modalId);
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
  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }
}
