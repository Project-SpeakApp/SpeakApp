import {Component, EventEmitter, Input, OnDestroy, Output} from '@angular/core';
import {FriendRequestGet} from "../../../../shared/types/profiles/FriendRequestPage";
import {Subscription} from "rxjs";
import {AlertService} from "../../../../shared/services/alert.service";
import {FriendsService} from "../../../profiles/services/friends.service";

@Component({
  selector: 'app-friend-request-card',
  templateUrl: './friend-request-card.component.html'
})
export class FriendRequestCardComponent implements OnDestroy {

  @Input() request: FriendRequestGet = {} as FriendRequestGet;
  @Output() removeRequest: EventEmitter<string> = new EventEmitter<string>();

  acceptRequestLoading = false;
  rejectRequestLoading = false;

  sub = new Subscription();

  constructor(private friendsService: FriendsService, private alertService: AlertService) {
  }

  acceptRequest() {
    this.acceptRequestLoading = true;
    this.sub.add(this.friendsService.acceptFriendRequest(this.request.id).subscribe((_) => {
      this.acceptRequestLoading = false;
      this.alertService.showAlert("Friend request accepted", "success");
      this.removeRequest.emit(this.request.id)
    }));
  }

  rejectRequest() {
    this.rejectRequestLoading = true;
    this.sub.add(this.friendsService.rejectFriendRequest(this.request.id).subscribe((_) => {
      this.rejectRequestLoading = false;
      this.alertService.showAlert("Friend request rejected", "success");
      this.removeRequest.emit(this.request.id)
    }));
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
