import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  alerts = signal([] as IAlert[]);
  count = 0;
  POSDEFAULT = '-left-1/2'
  POSSHOW = 'left-0'

  constructor() { }

  showAlert(message: string, type: 'success' | 'error' | 'info' | 'warning' = 'success') {
    const alert: IAlert = { message, type, id: this.count++, isShown: false, hide: false };
    this.alerts.mutate(alerts => alerts.push(alert));
    setTimeout(() => {
      alert.isShown = true;
      this.alerts.update(alerts => alerts.map(a => a.id === alert.id ? alert : a));
    }, 100);
    // delete alert after 5 seconds
    setTimeout(() => this.closeAlert(alert), 5000);
  }

  closeAlert(alert: IAlert) {
    this.alerts.update(alerts => alerts.map(a => a.id === alert.id ? { ...a, hide: true } : a));
    // tutaj miało być transition ale nie działa
    //setTimeout(() => this.removeAlert(alert), 1000);
    this.removeAlert(alert)
  }

  removeAlert(alert: IAlert) {
    this.alerts.update(alerts => alerts.filter(a => a.id !== alert.id));
  }
}

export interface IAlert {
  type: 'success' | 'error' | 'info' | 'warning';
  message: string;
  id: number;
  isShown: boolean;
  hide: boolean;
};
