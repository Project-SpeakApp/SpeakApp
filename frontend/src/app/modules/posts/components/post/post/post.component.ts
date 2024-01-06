import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {DateFormatting} from "../../../../../shared/util/DateFormatting";
import {AuthService} from "../../../../../shared/services/auth.service";


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnChanges, OnInit{
  @Input() post: PostGet = {} as PostGet;
  formattedDate: string = '';
  userId: string = '';
  isEdited: boolean = false;

  @Output() contentUpdated: EventEmitter<PostGet> = new EventEmitter<PostGet>();

  @Output() deleted: EventEmitter<string> = new EventEmitter<string>();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['post'] && this.post && this.post.createdAt) {
      this.formattedDate = DateFormatting.formatDateTime(this.post.createdAt);
    }
  }

  constructor(private authService: AuthService ) {

  }
  ngOnInit() {
    this.userId = this.authService.state().userId;
  }

  enableEditing(): void {
    if(this.isEdited) this.isEdited = false;
    else this.isEdited = true;
  }

  updateContent(updatedPost?: PostGet): void {
    this.isEdited = false;
    if(updatedPost) {
      this.post = updatedPost;
    }
  }

  handleDeletion(postId?: string): void {
    if(postId) {
      this.deleted.emit(postId);
    }
  }


}
