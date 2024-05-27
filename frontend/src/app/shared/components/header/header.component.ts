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
<<<<<<< HEAD
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private themeService: ThemeService, private chatService: ChatService, private router: Router) { }

  authState = this.authService.state;

  themes = this.themeService.themes;

  connectToWebSocket() {
    this.chatService.connect(this.authService.state().userId);
    this.router.navigate(['/chats']);
  }
  themeChanges(event: any) {
    this.themeService.changeTheme(event.target.value);
    console.log(event.target.value);
  }
=======
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
>>>>>>> main

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
