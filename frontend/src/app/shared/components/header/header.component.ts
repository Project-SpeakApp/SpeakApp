import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ThemeService } from '../../services/theme.service';
import {ChatService} from "../../../modules/chat/services/chat.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
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
}
