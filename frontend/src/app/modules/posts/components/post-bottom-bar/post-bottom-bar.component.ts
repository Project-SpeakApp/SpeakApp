import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {PostGet} from "../../../../shared/types/posts/post-get.model";
import {DateFormatting} from "../../../../shared/util/DateFormatting";
import { ReactionType } from 'src/app/shared/types/posts/ReactionType.enum';
import { ReactionService } from '../../sevices/reaction.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-post-bottom-bar',
  templateUrl: './post-bottom-bar.component.html',
  styleUrls: ['./post-bottom-bar.css']
})
export class PostBottomBarComponent implements OnInit, OnDestroy {
  @Input() post: PostGet = {} as PostGet;

  formattedDate: string = '';
  isVisible: boolean = true;
  constructor(private changeDetectorRef: ChangeDetectorRef) {}


  subscription: Subscription = new Subscription();

  // musze tak bo nie działają enumy w htmlu
  like = ReactionType.LIKE;
  love = ReactionType.LOVE;
  haha = ReactionType.HA_HA;
  wow = ReactionType.WOW;
  sad = ReactionType.SAD;
  wrr = ReactionType.WRR;

  constructor(private reactionService: ReactionService) { }


  checkIfPostWasEdited() {
    if(this.post.modifiedAt == null || (this.post.createdAt.valueOf() == this.post.modifiedAt.valueOf())) {
      this.isVisible = false;
    }
    else {
      this.isVisible = true;
      this.formattedDate = DateFormatting.formatDateTime(this.post.modifiedAt);
    }
  }

  upsertReaction(reactionType: ReactionType) {
    if (this.reactionService.isLoading()) return;
    this.reactionService.upsertReactionToPost(this.post.postId, reactionType, this.post.currentUserReaction)
      .subscribe(() => this.post.currentUserReaction = reactionType);
  }

  ngOnInit(): void {
    this.checkIfPostWasEdited();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
