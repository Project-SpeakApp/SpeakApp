import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-author-preview',
  templateUrl: './author-preview.component.html'
})
export class AuthorPreviewComponent {
  @Input() formattedDate: string = '';
  @Input() fullName: string = '';
  @Input() isComment: boolean = false;
}
