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
      const oldReaction = this.post.currentUserReaction;
      this.post.currentUserReaction = newReactionType;

      // update reaction type count
      if (oldReaction !== null) {
        this.post.reactions.sumOfReactionsByType.set(
          oldReaction,
          this.post.reactions.sumOfReactionsByType.get(oldReaction)! - 1,
        );
      }
      if (newReactionType !== null) {
        const currentCount = this.post.reactions.sumOfReactionsByType.get(newReactionType);
        if (currentCount) {
          this.post.reactions.sumOfReactionsByType.set(newReactionType, currentCount + 1);
        } else {
          this.post.reactions.sumOfReactionsByType.set(newReactionType, 1);
        }
      }

      // update overall count
      if (oldReaction === null && newReactionType !== null) {
        this.post.reactions.sumOfReactions++;
      } else if (oldReaction !== null && newReactionType === null) {
        this.post.reactions.sumOfReactions--;
      }

      // rerender component
      this.post = {...this.post};
  }

  ngOnInit() {
    this.userId = this.authService.state().userId;
  }
}
