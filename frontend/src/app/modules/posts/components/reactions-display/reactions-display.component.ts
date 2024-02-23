import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ReactionType} from "../../../../shared/types/posts/ReactionType.enum";

@Component({
  selector: 'app-reactions-display',
  templateUrl: './reactions-display.component.html'
})
export class ReactionsDisplayComponent implements OnInit, OnChanges {
  @Input() sortedReactions: [ReactionType, number][] = [];
  @Input() sumOfReactions: number = 0;
  @Input() isComment?: boolean = false;

  // musze tak bo nie działają enumy w htmlu
  like = ReactionType.LIKE;
  love = ReactionType.LOVE;
  haha = ReactionType.HA_HA;
  wow = ReactionType.WOW;
  sad = ReactionType.SAD;
  wrr = ReactionType.WRR;


  ngOnChanges(changes: SimpleChanges) {
    if (changes) {
      this.ngOnInit();
    }
  }

  ngOnInit() {
    if (this.isComment) console.log(this.sortedReactions);
  }
}
