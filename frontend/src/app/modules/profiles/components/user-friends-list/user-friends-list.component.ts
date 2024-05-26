import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {UserGet} from "../../../../shared/types/profiles/user-get.model";
import {FriendsService} from "../../services/friends.service";

@Component({
  selector: 'app-user-friends-list',
  templateUrl: './user-friends-list.component.html'
})
export class UserFriendsListComponent implements OnInit, OnDestroy{

  users: UserGet[] = [];
  isLoading = false;
  pageNumber: number = 0;
  totalPages: number = 0;

  @Input() userId: string = '';

  subscription = new Subscription();

  constructor(private friendsService: FriendsService) {}

  onScroll() {
    this.loadFriends();
  }

  loadFriends() {
    if(this.totalPages === this.pageNumber && this.pageNumber !== 0) return;
    this.isLoading = true;
    this.subscription.add(this.friendsService.getFriends(this.userId, this.pageNumber, 50).subscribe({
      next: (response) => {
        this.users = [...this.users, ...response.userPreviews];
        this.pageNumber = response.currentPage + 1;
        this.isLoading = false;
        this.totalPages = response.totalPages;
      },
      error: (error) => {
        this.isLoading = false;
      },
    }));
  }

  ngOnInit() {
    this.loadFriends();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
