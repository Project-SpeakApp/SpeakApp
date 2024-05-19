import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {FriendRequestGet} from "../../../../shared/types/profiles/FriendRequestPage";
import {FriendsService} from "../../../profiles/services/friends.service";
import {FriendStatus} from "../../../profiles/types/ProfileGetDTO";

@Component({
  selector: 'app-friend-request-list',
  templateUrl: './friend-request-list.component.html'
})
export class FriendRequestListComponent implements OnInit, OnDestroy {

  requests: FriendRequestGet[] = [];
  isLoading = false;
  pageNumber: number = 0;
  totalPages: number = 0;

  subscription = new Subscription();

  constructor(private friendService: FriendsService) {
  }

  onScroll() {
    this.loadRequests();
  }

  loadRequests() {
    if(this.totalPages === this.pageNumber && this.pageNumber !== 0) return;
    this.isLoading = true;
    this.subscription = this.friendService.getFriendRequests(this.pageNumber, 50).subscribe({
      next: (response) => {
        this.requests = [...this.requests, ...response.friendRequests];
        this.pageNumber = response.currentPage + 1;
        this.isLoading = false;
        this.totalPages = response.totalPages;
      },
      error: (error) => {
        this.isLoading = false;
      },
    });
  }

  removeRequest(requestId: string) {
    this.requests = this.requests.filter((request) => request.id !== requestId);
  }

  ngOnInit() {
    this.loadRequests();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  protected readonly FriendStatus = FriendStatus;
}
