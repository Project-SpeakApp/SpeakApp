import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PostService} from '../../sevices/post.service';
import {Subscription} from 'rxjs';
import {AlertService} from '../../../../shared/services/alert.service';
import {AddPost} from "../../../../shared/types/posts/add-post.model";
import {PostGet} from "../../../../shared/types/posts/post-get.model";
import ImageSnippet from "../../../../shared/types/media/ImageSnippet";
import {ImageService} from "../../../../shared/services/image.service";
import TypeMedia from "../../../../shared/types/media/type-media";

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

  selectedFile: ImageSnippet | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService,
    private alertService: AlertService,
    private imageService: ImageService
  ) {
    this.model = {
      content: ''
    };
  }

  onFormSubmit(): void {
    if (this.myForm.valid) {
      this.isLoading = true;
      this.model.content = this.myForm.value.content;
      this.addPostSubscription = this.postService.addPost(this.model).subscribe(
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

  processImage(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      this.imageService.uploadImage(file, TypeMedia.IMAGE).subscribe((res) => {
        this.imageService.downloadImage(res).subscribe((blob) => {
          const imageUrl = URL.createObjectURL(blob);
          this.selectedFile = new ImageSnippet(imageUrl, file);
        });
      });
    })

    reader.readAsDataURL(file);
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
