import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AlertService} from "../../../../shared/services/alert.service";
import {PostService} from "../../sevices/post.service";
import {AddPost} from "../../../../shared/types/posts/add-post.model";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";
import {PostGet} from "../../../../shared/types/posts/post-get.model";
import {CalculateNumberOfRows} from "../../../../shared/util/CalculateNumberOfRows";

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['../../css-files/edit-style-sheet.component.css']
})
export class EditPostComponent implements OnInit, OnDestroy{
  @Input() currentContent: string = "";
  @Input() imageId: string | null = null;
  @Input() postId: string = "";
  @Input() authorId: string = "";

  @Output() contentUpdated: EventEmitter<PostGet> = new EventEmitter<PostGet>();
  @Output() imageDeleted: EventEmitter<void> = new EventEmitter<void>();

  initContent: string = ""
  myForm!: FormGroup;

  visible: boolean = false;

  private UpdatePostSubscription?: Subscription;
  model: AddPost;
  isLoading: boolean = false;
  numberOfRows: number = 0;


  constructor(private formBuilder: FormBuilder, private alertService: AlertService, private postService: PostService, private authService: AuthService) {
    this.model = {
      content: '',
      mediaId: null,
    };
  }

  onFormSubmit(): void {
    if (this.myForm.valid && this.initContent !== this.myForm.value.content) {
      this.isLoading = true;
      this.currentContent = this.myForm.value.content;
      this.model.content = this.currentContent;
      this.model.mediaId = this.imageId;
      this.UpdatePostSubscription = this.postService.updatePost(this.postId, this.model).subscribe(
        (updatedPost) => { this.alertService.showAlert('Post updated successfully', 'success'), this.contentUpdated.emit(updatedPost), this.isLoading = false;},
        (error) => {this.isLoading = false;}

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

  deleteImage() {
    this.imageId = null;
    this.imageDeleted.emit();
  }

  ngOnInit(): void {
    this.initContent = this.currentContent;
    if(this.authorId === this.authService.state().userId) this.visible = true;
    this.myForm = this.formBuilder.group({
      content: [this.currentContent, [Validators.required, Validators.minLength(1)]]
    });
    this.numberOfRows = CalculateNumberOfRows.calculateNumberOfRows(this.currentContent);
  }


  ngOnDestroy(): void {
    this.UpdatePostSubscription?.unsubscribe();
  }

}
