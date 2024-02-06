import {Component, Input, OnInit} from '@angular/core';
import {ReactionsGet} from "../../../../shared/types/posts/reactions-get.model";
import {ReactionType} from "../../../../shared/types/posts/ReactionType.enum";

@Component({
  selector: 'app-reactions-display',
  templateUrl: './reactions-display.component.html'
})
export class ReactionsDisplayComponent implements OnInit {
  @Input() reactions: ReactionsGet = {} as ReactionsGet;
  @Input() isComment?: boolean = false;

  // musze tak bo nie działają enumy w htmlu
  like = ReactionType.LIKE;
  love = ReactionType.LOVE;
  haha = ReactionType.HA_HA;
  wow = ReactionType.WOW;
  sad = ReactionType.SAD;
  wrr = ReactionType.WRR;

  sortedReactions: [ReactionType, number][] = [];

  ngOnInit() {
    this.sortedReactions = [...this.reactions.sumOfReactionsByType.entries()]
      .filter((reaction) => reaction[1] > 0)
      .sort((a, b) => b[1] - a[1]);
  }
}
