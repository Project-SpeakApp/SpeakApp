import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {PostService} from "../../sevices/post.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";

@Component({
  selector: 'app-delete-post',
  templateUrl: './delete-post.component.html',
  styleUrls: ['./delete-post.component.css']
})
export class DeletePostComponent implements OnDestroy, OnInit{
  private addPostSubscription?: Subscription;

  @Input() postId: string = "";
  @Input() authorId: string = "";

  visible: boolean = true;

  constructor(private postService: PostService, private authService: AuthService) {
  }

  ngOnInit() {
    if (this.authService.state().userId === this.authorId) {
      this.visible = true;
    }
  }

  onFormSubmit(): void {
      this.addPostSubscription = this.postService.deletePost( this.postId, this.authService.state().userId).subscribe();
  }
  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }
}
