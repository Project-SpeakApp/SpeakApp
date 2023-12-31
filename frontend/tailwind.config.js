/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
    },
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: [
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
      {
        theme1: {
          "primary": "#2DD4BF",
          "secondary": "#EF9FBC",
          "accent": "#EEAF3A",
          "neutral": "#3d4451",
          "base-100": "#FAF7F5",
        },
        theme2: {
          "primary": "#F5D268",
          "secondary": "#AF9F85",
          "accent": "#000000",
          "neutral": "#3d4451",
          "base-100": "#ffffff",
        },
        theme3: {
          "primary": "#2563EB",
          "secondary": "#7C3AED",
          "accent": "#EC4899",
          "neutral": "#3d4451",
          "base-100": "#ffffff",
        },
      },
    ],
  },
}

