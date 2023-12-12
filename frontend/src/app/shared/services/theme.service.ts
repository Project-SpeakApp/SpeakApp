import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  constructor() { }

  themes = [
      "light",
      "dark",
      "cupcake",
      "bumblebee",
      "emerald",
      "corporate",
      "synthwave",
      "retro",
      "cyberpunk",
      "valentine",
      "halloween",
      "garden",
      "forest",
      "aqua",
      "lofi",
      "pastel",
      "fantasy",
      "wireframe",
      "black",
      "luxury",
      "dracula",
      "cmyk",
      "autumn",
      "business",
      "acid",
      "lemonade",
      "night",
      "coffee",
      "winter",
      "dim",
      "nord",
      "sunset",
      "theme1",
      "theme2",
      "theme3"
  ]

  currentTheme = signal("cupcake");

  loadTheme() {
    const theme = localStorage.getItem('theme');
    if (theme) {
      this.currentTheme.set(theme);
    }
  }

  changeTheme(theme: string) {
    this.currentTheme.set(theme);
    localStorage.setItem('theme', theme);
  }
}
