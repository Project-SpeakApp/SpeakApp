import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { PostService } from '../../sevices/post.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/services/alert.service';
import {AddPost} from "../../../../shared/types/posts/add-post.model";
import {AuthService} from "../../../../shared/services/auth.service";
import {PostGet} from "../../../../shared/types/posts/post-get.model";

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit, OnDestroy {
  @Output() contentAdded: EventEmitter<PostGet> = new EventEmitter<PostGet>();
  private addPostSubscription?: Subscription;
  model: AddPost;
  isLoading: boolean = false;

  myForm!: FormGroup;


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

  onFormSubmit(): void {
    if (this.myForm.valid) {
      this.isLoading = true;
      this.model.content = this.myForm.value.content;
      this.addPostSubscription = this.postService.addPost(this.model, this.authService.state().userId).subscribe(
        (newPost) => {
          this.contentAdded.emit(newPost);
          this.isLoading = false;
          this.myForm.reset();
        },
        (error) => {
          this.isLoading = false;
        }
      );
    } else {
      this.alertService.showAlert('Type Content', 'error');
    }
  }

  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.minLength(1)]]
    });
  }
  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }
}
