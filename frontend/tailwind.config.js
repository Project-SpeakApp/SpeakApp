/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      animation: {
          'fade': 'fade 1s ease-in-out reverse',
      },
      keyframes: {
          fade: {
            '0%': { opacity: '0' },
            '100%': { opacity: '1' },
          }
      }
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

