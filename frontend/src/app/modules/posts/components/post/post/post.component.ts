import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {DateFormatting} from "../../../../../shared/util/DateFormatting";
import {AuthService} from "../../../../../shared/services/auth.service";
import { ReactionType } from 'src/app/shared/types/posts/ReactionType.enum';


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnChanges, OnInit{
  @Input() post: PostGet = {} as PostGet;
  @Output() deleted: EventEmitter<string> = new EventEmitter<string>();
  @Output() contentUpdated: EventEmitter<PostGet> = new EventEmitter<PostGet>();
  @Output() changeReactionEmitter: EventEmitter<{reaction: ReactionType | null, postId: string}> = new EventEmitter<{reaction: ReactionType | null, postId: string}>();

  formattedDate: string = '';
  userId: string = '';
  isEdited: boolean = false;


  constructor(private authService: AuthService ) {

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

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['post'] && this.post && this.post.createdAt) {
      this.formattedDate = DateFormatting.formatDateTime(this.post.createdAt);
    }
  }

  changeReaction(newReactionType: ReactionType | null): void {
    this.changeReactionEmitter.emit({reaction: newReactionType, postId: this.post.postId});
  }

  ngOnInit() {
    this.userId = this.authService.state().userId;
  }
}
