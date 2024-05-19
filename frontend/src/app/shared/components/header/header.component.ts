import {Component, effect, OnDestroy, OnInit} from '@angular/core';
import { AuthService } from '../../services/auth.service';
import {FriendsService} from "../../../modules/profiles/services/friends.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit, OnDestroy {

  constructor(private authService: AuthService, private friendsService: FriendsService) {
    effect(() => {
      if (!this.authState().isLoggedIn) return;
      this.sub.add(this.friendsService.getFriendRequests(0, 99).subscribe(page => {
        this.requestsCount = page.friendRequests.length;
      }));
    });
  }

  authState = this.authService.state;

  requestsCount = 0;
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
