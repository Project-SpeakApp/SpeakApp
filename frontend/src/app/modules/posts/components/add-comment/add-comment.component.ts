import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PostService} from "../../sevices/post.service";
import {AuthService} from "../../../../shared/services/auth.service";
import {AddComment} from "../../../../shared/types/posts/add-comment.model";
import {AlertService} from "../../../../shared/services/alert.service";

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.css']
})
export class AddCommentComponent implements OnInit{
    isLoading: boolean = false;
    model: AddComment;

    myForm!: FormGroup;
    constructor(private formBuilder: FormBuilder, private postService: PostService,
                private authService: AuthService, private alertService: AlertService) {
      this.model = {
        content: ''
      };
    }

  ngOnInit(): void {
      this.myForm = this.formBuilder.group({
        content: ['', [Validators.required, Validators.minLength(1)]]
      });
  }
}
