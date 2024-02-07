import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-sort-comments',
  templateUrl: './sort-comments.component.html',
  styleUrls: ['./sort-comments.component.css']
})
export class SortCommentsComponent {
  @Output() sort: EventEmitter<{ sortBy: string; sortOrder: string }> = new EventEmitter<{ sortBy: string; sortOrder: string }>();
  sortOrderTitle: string = "Descending ⮟";
  sortByTitle: string = "date";
  sortBy: string = "createdAt";
  sortOrder: string = "DESC";

  changeOrder() : void {
    if(this.sortOrderTitle == "Descending ⮟") {
      this.sortOrderTitle = "Ascending ⮝";
      this.sortOrder = "ASC";
    }
    else this.sortOrderTitle = "Descending ⮟", this.sortOrder = "DESC";
    this.sort.emit({ sortBy: this.sortBy, sortOrder: this.sortOrder });
  }

  changeParameter(parameter: string) : void {
    if(parameter === this.sortByTitle) return;
    else if(parameter === 'date') this.sortByTitle = parameter, this.sortBy = "createdAt";
    else if(parameter === 'reactions') this.sortByTitle = parameter, this.sortBy = "numberOfReactions";
    this.sort.emit({ sortBy: this.sortBy, sortOrder: this.sortOrder });
  }
}
