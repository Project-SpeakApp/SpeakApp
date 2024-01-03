import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostService } from '../../sevices/post.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/services/alert.service';
import {AddPost} from "../../../../shared/types/posts/add-post.model";
import {AuthService} from "../../../../shared/services/auth.service";

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit, OnDestroy {
  myForm!: FormGroup;
  private addPostSubscription?: Subscription;
  model: AddPost;
  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService,
    private alertService: AlertService,
    private authService: AuthService,
  ) {
    this.model = {
      content: ''
    };
  }
    isLoading = this.postService.isLoadingAdd;
  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  onFormSubmit(): void {
    if (this.myForm.valid) {
      this.model.content = this.myForm.value.content;
      this.addPostSubscription = this.postService.addPost(this.model, this.authService.state().userId).subscribe();
    } else {
      this.alertService.showAlert('Type Content', 'error');
    }
  }

  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }
}
