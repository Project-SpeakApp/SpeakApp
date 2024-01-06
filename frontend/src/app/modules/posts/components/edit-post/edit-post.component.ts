import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AlertService} from "../../../../shared/services/alert.service";
import {PostService} from "../../sevices/post.service";
import {AddPost} from "../../../../shared/types/posts/add-post.model";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";
import {PostGet} from "../../../../shared/types/posts/post-get.model";

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit, OnDestroy{
  @Input() currentContent: string = "";

  @Input() postId: string = "";

  @Input() authorId: string = "";
  initContent: string = ""
  myForm!: FormGroup;

  visible: boolean = false;

  private UpdatePostSubscription?: Subscription;


  isLoading = this.postService.isLoadingUpdate;

  @Output() contentUpdated: EventEmitter<PostGet> = new EventEmitter<PostGet>();


  model: AddPost;
  constructor(private formBuilder: FormBuilder, private alertService: AlertService, private postService: PostService, private authService: AuthService) {
    this.model = {
      content: ''
    };
  }
  ngOnInit(): void {
    this.initContent = this.currentContent;
    if(this.authorId === this.authService.state().userId) this.visible = true;
    this.myForm = this.formBuilder.group({
      content: [this.currentContent, [Validators.required, Validators.minLength(1)]]
    });
  }

  onFormSubmit(): void {
    if (this.myForm.valid && this.initContent !== this.myForm.value.content) {
      this.currentContent = this.myForm.value.content;
      this.model.content = this.currentContent;
      this.UpdatePostSubscription = this.postService.updatePost(this.postId, this.model, this.authService.state().userId).subscribe(
        (updatedPost) => { this.alertService.showAlert('Post updated successfully', 'success'), this.contentUpdated.emit(updatedPost)}

      );
    } else if (this.initContent === this.myForm.value.content) {
      this.closeForm();
    } else {
      this.alertService.showAlert('Type content', 'error');
    }
  }

  closeForm() {
    this.contentUpdated.emit();
  }



  ngOnDestroy(): void {
    this.UpdatePostSubscription?.unsubscribe();
  }

}
