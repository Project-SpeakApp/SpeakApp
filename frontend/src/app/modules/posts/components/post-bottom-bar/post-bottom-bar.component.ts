import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges
} from '@angular/core';
import { PostGet } from '../../../../shared/types/posts/post-get.model';
import { DateFormatting } from '../../../../shared/util/DateFormatting';
import { ReactionType } from 'src/app/shared/types/posts/ReactionType.enum';
import { ReactionService } from '../../sevices/reaction.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-post-bottom-bar',
  templateUrl: './post-bottom-bar.component.html',
  styleUrls: ['./post-bottom-bar.component.css'],
})
export class PostBottomBarComponent implements OnInit, OnDestroy, OnChanges {
  @Input() post: PostGet = {} as PostGet;
  @Output() changeReaction: EventEmitter<ReactionType | null> = new EventEmitter<ReactionType | null>();

  formattedDate: string = '';
  isVisible: boolean = true;

  subscription: Subscription = new Subscription();

  postReactionTypesCount = 0;
  sortedReactions: [ReactionType, number][] = [];

  // musze tak bo nie działają enumy w htmlu
  like = ReactionType.LIKE;
  love = ReactionType.LOVE;
  haha = ReactionType.HA_HA;
  wow = ReactionType.WOW;
  sad = ReactionType.SAD;
  wrr = ReactionType.WRR;

  constructor(private reactionService: ReactionService) {}

  checkIfPostWasEdited() {
    if (this.post.modifiedAt == null || this.post.createdAt.valueOf() == this.post.modifiedAt.valueOf()) {
      this.isVisible = false;
    } else {
      this.isVisible = true;
      this.formattedDate = DateFormatting.formatDateTime(this.post.modifiedAt);
    }
  }

  updateReaction(reactionType: ReactionType | null) {
    const newReactionType = reactionType === this.post.currentUserReaction ? null : reactionType;
    this.changeReaction.emit(newReactionType);
  }

  upsertReaction(reactionType: ReactionType) {
    if (this.reactionService.isLoading()) return;
    this.subscription = this.reactionService
      .upsertReactionToPost(this.post.postId, reactionType, this.post.currentUserReaction)
      .subscribe(() => this.updateReaction(reactionType));
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['post']) {
      this.ngOnInit();
    }
  }

  ngOnInit(): void {
    this.checkIfPostWasEdited();
    this.postReactionTypesCount = this.post.reactions.sumOfReactionsByType.size;
    this.sortedReactions = [...this.post.reactions.sumOfReactionsByType.entries()]
      .filter((reaction) => reaction[1] > 0)
      .sort((a, b) => b[1] - a[1]);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
