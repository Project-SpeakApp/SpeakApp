import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {PostGet} from "../../../../../shared/types/posts/post-get.model";
import {DateFormatting} from "../../../../../shared/util/DateFormatting";


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnChanges{
  @Input() post: PostGet = {} as PostGet;
  formattedDate: string = '';

  isEdited: boolean = false;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['post'] && this.post && this.post.createdAt) {
      this.formattedDate = DateFormatting.formatDateTime(this.post.createdAt);
    }
  }

  enableEditing(): void {
    if(this.isEdited) this.isEdited = false;
    else this.isEdited = true;
  }

  updateContent(): void {
    this.isEdited = false;
  }

}
