import {Component, effect, OnDestroy, OnInit} from '@angular/core';
import { AuthService } from '../../services/auth.service';
import {Subscription} from "rxjs";
import {FriendsService} from "../../../modules/profiles/services/friends.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit, OnDestroy {

  constructor(private authService: AuthService, private friendsService: FriendsService) {
    effect(() => {
      if (this.authState().isLoggedIn) {
        this.sub.add(this.friendsService.getFriendRequests(0, 99).subscribe((count) => {
          this.requestsCount.set(count.friendRequests.length);
        }));
      }
    });
  }

  authState = this.authService.state;

  requestsCount = this.authService.requestCount;
  sub = new Subscription();

  async logoutUser() {
    await this.authService.logout('http://localhost:4200/');
  }

  async loginUser() {
    await this.authService.login();
  }

  async registerUser() {
    await this.authService.register();
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
