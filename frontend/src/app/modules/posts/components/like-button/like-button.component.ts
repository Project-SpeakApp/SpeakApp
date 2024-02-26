import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ReactionType} from "../../../../shared/types/posts/ReactionType.enum";

@Component({
  selector: 'app-like-button',
  templateUrl: './like-button.component.html',
})
export class LikeButtonComponent {
  @Input() currentUserReaction: ReactionType | null = null;
  @Input() isComment?: boolean = false;

  @Output() changeReaction: EventEmitter<ReactionType> = new EventEmitter<ReactionType>();

  // musze tak bo nie działają enumy w htmlu
  like = ReactionType.LIKE;
  love = ReactionType.LOVE;
  haha = ReactionType.HA_HA;
  wow = ReactionType.WOW;
  sad = ReactionType.SAD;
  wrr = ReactionType.WRR;
}
