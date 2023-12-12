import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {ReactionsGet} from "../../../../../shared/types/posts/reactions-get.model";
import {UserGet} from "../../../../../shared/types/profiles/user-get.model";
import {ReactionType} from "../../../../../shared/types/posts/ReactionType.enum";
import {DateFormatting} from "../../../../../shared/util/DateFormatting";


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnChanges{
  @Input() post: PostGet = {} as PostGet;
  formattedDate: string = '';


  ngOnChanges(changes: SimpleChanges): void {
    if (changes['post'] && this.post && this.post.createdAt) {
      this.formattedDate = DateFormatting.formatDateTime(this.post.createdAt);
    }
  }

}
