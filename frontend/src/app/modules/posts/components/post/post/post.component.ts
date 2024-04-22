import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  signal,
  SimpleChanges
} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {DateFormatting} from "../../../../../shared/util/DateFormatting";
import {AuthService} from "../../../../../shared/services/auth.service";
import { ReactionType } from 'src/app/shared/types/posts/ReactionType.enum';
import {ImageService} from "../../../../../shared/services/image.service";
import {Subscription} from "rxjs";
import {PostService} from "../../../sevices/post.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnChanges, OnInit, OnDestroy {
  @Input() post: PostGet = {} as PostGet;
  @Output() deleted: EventEmitter<string> = new EventEmitter<string>();
  @Output() contentUpdated: EventEmitter<PostGet> = new EventEmitter<PostGet>();

  formattedDate: string = '';
  userId: string = '';
  isEdited: boolean = false;

  imageSub = new Subscription();
  imageLoading = signal(false);
  imageUrl: string | null = null;

  constructor(
    private authService: AuthService,
    private imageService: ImageService,
    private postService: PostService,
    private router: Router) {
  }

  enableEditing(): void {
    this.isEdited = !this.isEdited;
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

  handleCommentAdding() {
    this.post.totalNumberOfComments++;
  }

  handleCommentDeletion() {
    this.post.totalNumberOfComments--;
  }

  removeImage() {
    this.post.mediaId = null;
    this.imageUrl = null;
    this.imageSub.add(this.postService.updatePost(this.post.postId, {content: this.post.content, mediaId: null}).subscribe(
      (updatedPost) => {this.contentUpdated.emit(updatedPost);}
    ));
  }

  async redirectToProfile() {
    await this.router.navigate(['/profiles', this.post.author.userId]);
  }

  ngOnInit() {
    this.userId = this.authService.state().userId;
    if (this.post.mediaId) {
      this.imageLoading.set(true);
      this.imageSub.add(this.imageService.downloadImage(this.post.mediaId).subscribe(
        (blob) => {
          this.imageUrl = URL.createObjectURL(blob);
          this.imageLoading.set(false);
        }
      ));
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['post'] && this.post && this.post.createdAt) {
      this.formattedDate = DateFormatting.formatDateTime(this.post.createdAt);
    }
  }

  ngOnDestroy() {
    this.imageSub.unsubscribe();
  }
}
