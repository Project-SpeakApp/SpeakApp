import { Component } from '@angular/core';
import { AlertService } from './services/alert.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'SpeakApp';

  constructor(private alertService: AlertService) { }

  alerts = this.alertService.alerts;
  count = 0;

  onShowAlert() {
    this.alertService.showAlert('This is an alert!' + this.count++, 'success');
  }

  ngOnInit() {
    this.alertService.showAlert('Welcome to SpeakApp!', 'info');
  }
}
