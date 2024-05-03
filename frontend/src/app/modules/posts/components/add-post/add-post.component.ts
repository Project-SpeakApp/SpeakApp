import {Component, ElementRef, EventEmitter, OnDestroy, OnInit, Output, signal, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PostService} from '../../sevices/post.service';
import {pipe, Subscription, tap} from 'rxjs';
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

  @ViewChild('imageInput')
  fileInputElement: ElementRef = new ElementRef(null);

  imageLoading = signal(false);
  deleteImageLoading = signal(false);

  imageSubscription = new Subscription();

  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService,
    private alertService: AlertService,
    private imageService: ImageService
  ) {
    this.model = {
      content: '',
      mediaId: null,
    };
  }

  onFormSubmit(): void {
    if (this.myForm.valid) {
      this.isLoading = true;
      this.model.content = this.myForm.value.content;
      this.model.mediaId = this.selectedFile?.guid ? this.selectedFile.guid : null;
      this.addPostSubscription = this.postService.addPost(this.model).subscribe(
        (newPost) => {
          this.contentAdded.emit(newPost);
          this.isLoading = false;
          this.myForm.reset();
          this.selectedFile = null;
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
    if (this.selectedFile) {
      this.deleteImage();
    }

    this.imageLoading.set(true);
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      this.imageSubscription.add(this.imageService.uploadImage(file, TypeMedia.IMAGE).pipe(
        tap(
          (res: string) => {
            this.imageSubscription.add(this.imageService.downloadImage(res).subscribe((blob) => {
              const imageUrl = URL.createObjectURL(blob);
              this.selectedFile = new ImageSnippet(imageUrl, file, res);
              this.imageLoading.set(false);
            }));
          },
          (error) => {
            this.imageLoading.set(false);
          }
        )
      ).subscribe());
    })

    reader.readAsDataURL(file);
  }

  deleteImage() {
    if (!this.selectedFile) return;

    this.deleteImageLoading.set(true);
    this.imageSubscription.add(this.imageService.deleteImage(this.selectedFile.guid).subscribe(() => {
      this.selectedFile = null;
      this.fileInputElement.nativeElement.value = '';
      this.deleteImageLoading.set(false);
    }));
  }

  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.minLength(1)]]
    });
  }
  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
    this.imageSubscription.unsubscribe();
  }
}
