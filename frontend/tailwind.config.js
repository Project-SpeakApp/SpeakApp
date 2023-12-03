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

