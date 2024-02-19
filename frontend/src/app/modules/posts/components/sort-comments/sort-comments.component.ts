import {Component, EventEmitter, Output} from '@angular/core';
import {SortOrder} from "../../../../shared/types/posts/SortOrder.enum";

@Component({
  selector: 'app-sort-comments',
  templateUrl: './sort-comments.component.html'
})
export class SortCommentsComponent {
  @Output() sort: EventEmitter<{ sortBy: string; sortOrder: SortOrder }> = new EventEmitter<{ sortBy: string; sortOrder: SortOrder }>();
  sortOrderTitle: string = "Descending ⮟";
  sortByTitle: string = "date";
  sortBy: string = "createdAt";
  sortOrder: SortOrder = SortOrder.DESC;

  changeOrder() : void {
    if(this.sortOrderTitle == "Descending ⮟") {
      this.sortOrderTitle = "Ascending ⮝";
      this.sortOrder = SortOrder.ASC;
    }
    else this.sortOrderTitle = "Descending ⮟", this.sortOrder = SortOrder.DESC;
    this.sort.emit({ sortBy: this.sortBy, sortOrder: this.sortOrder });
  }

  changeParameter(parameter: string) : void {
    if(parameter === this.sortByTitle) return;
    else if(parameter === 'date') this.sortByTitle = parameter, this.sortBy = "createdAt";
    else if(parameter === 'reactions') this.sortByTitle = parameter, this.sortBy = "numberOfReactions";
    this.sort.emit({ sortBy: this.sortBy, sortOrder: this.sortOrder });
  }

  removeFocus() {
    const dropdownMenu = document.getElementById('dropdownMenu');
    if (dropdownMenu) {
      dropdownMenu.blur();
    }
  }
}
