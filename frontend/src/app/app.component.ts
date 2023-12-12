import { Component, OnInit, effect } from '@angular/core';
import { AlertService } from './shared/services/alert.service';
import { ThemeService } from './shared/services/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  title = 'SpeakApp';

  constructor(private themeService: ThemeService) {
    effect(() => {
      document.documentElement.setAttribute('data-theme', this.currentTheme());
      console.log(this.currentTheme());
    });
  }

  currentTheme = this.themeService.currentTheme;

  ngOnInit() {
    this.themeService.loadTheme();
  }
}
