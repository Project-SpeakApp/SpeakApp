import {Component, effect, OnDestroy, OnInit} from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ThemeService } from '../../services/theme.service';
import {ChatService} from "../../../modules/chat/services/chat.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {FriendsService} from "../../../modules/profiles/services/friends.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit, OnDestroy {

  constructor(private authService: AuthService, private friendsService: FriendsService, private themeService: ThemeService, private chatService: ChatService, private router: Router) {
    effect(() => {
      if (this.authState().isLoggedIn) {
        this.sub.add(this.friendsService.getFriendRequests(0, 99).subscribe((count) => {
          this.requestsCount.set(count.friendRequests.length);
        }));
      }
    });
  }

  themes = this.themeService.themes;

  connectToWebSocket() {
    this.chatService.connect(this.authService.state().userId);
    this.router.navigate(['/chats']);
  }

  authState = this.authService.state;

  requestsCount = this.authService.requestCount;
  sub = new Subscription();

  themeChanges(event: any) {
    this.themeService.changeTheme(event.target.value);
    console.log(event.target.value);
  }

  async logoutUser() {
    await this.authService.logout('https://localhost:4443/');
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
