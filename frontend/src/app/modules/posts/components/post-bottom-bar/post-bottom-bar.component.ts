import {
  ChangeDetectorRef,
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges
} from '@angular/core';
import {PostGet} from "../../../../shared/types/posts/post-get.model";
import {DateFormatting} from "../../../../shared/util/DateFormatting";

@Component({
  selector: 'app-post-bottom-bar',
  templateUrl: './post-bottom-bar.component.html',
  styleUrls: ['./post-bottom-bar.component.css']
})
export class PostBottomBarComponent implements OnInit, OnChanges{

  @Input() post: PostGet = {} as PostGet;

  formattedDate: string = '';
  isVisible: boolean = true;
  constructor(private changeDetectorRef: ChangeDetectorRef) {}


  checkIfPostWasEdited() {
    if(this.post.modifiedAt == null || (this.post.createdAt.valueOf() == this.post.modifiedAt.valueOf())) {
      this.isVisible = false;
    }
    else {
      this.isVisible = true;
      this.formattedDate = DateFormatting.formatDateTime(this.post.modifiedAt);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['post']) {
      this.changeDetectorRef.detectChanges();
      this.checkIfPostWasEdited();
    }
  }

  ngOnInit(): void {
    this.checkIfPostWasEdited();
  }
}
