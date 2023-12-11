import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {ReactionsGet} from "../../../../../shared/types/posts/reactions-get.model";
import {UserGet} from "../../../../../shared/types/profiles/user-get.model";
import {ReactionType} from "../../../../../shared/types/posts/ReactionType.enum";


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
      const createdAt = new Date(this.post.createdAt);
      const year = createdAt.getFullYear();
      const month = String(createdAt.getMonth() + 1).padStart(2, '0');
      const day = String(createdAt.getDate()).padStart(2, '0');
      const hours = String(createdAt.getHours()).padStart(2, '0');
      const minutes = String(createdAt.getMinutes()).padStart(2, '0');
      this.formattedDate = `${year}-${month}-${day} ${hours}:${minutes}`;
    }
  }

}
