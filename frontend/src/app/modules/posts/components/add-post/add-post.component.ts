import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostService } from '../../sevices/post.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/services/alert.service';
import {AddPost} from "../../../../shared/types/posts/add-post.model";

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit, OnDestroy {
  myForm!: FormGroup;
  userId = '6c84fb95-12c4-11ec-82a8-0242ac130003'; // give or get later some userId
  private addPostSubscription?: Subscription;
  model: AddPost;
  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService,
    private alertService: AlertService
  ) {
    this.model = {
      content: ''
    };
  }
    isLoading = this.postService.isLoading;
  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  onFormSubmit(): void {
    if (this.myForm.valid) {
      this.model.content = this.myForm.value.content;
      this.addPostSubscription = this.postService.addPost(this.model, this.userId).subscribe();
    } else {
      this.alertService.showAlert('Type Content', 'error');
    }
  }

  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }
}
