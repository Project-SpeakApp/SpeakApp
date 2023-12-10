import {Component, OnDestroy} from '@angular/core';
import {AddPost} from "../../../../shared/types/posts/add-post.model";
import {FormsModule} from "@angular/forms";
import {PostService} from "../../sevices/post.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnDestroy {
  model: AddPost;
  userId: string = '6c84fb95-12c4-11ec-82a8-0242ac130003'; //give or get later some userId
  private addPostSubscription?: Subscription

  constructor(private PostService: PostService) {
    this.model = {
      content: ''
    };
  }
  onFormSubmit() {
    this.addPostSubscription = this.PostService.addPost(this.model, this.userId).subscribe({
      next: (response) => {
        console.log("Success");
      },
      error: (error) => {
        console.log("Error");
      }
    })
  }

  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe();
  }

}
