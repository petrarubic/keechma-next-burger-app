const defaultTheme = require('tailwindcss/defaultTheme')
const colors = require('tailwindcss/colors')

module.exports = {
  future: {
    removeDeprecatedGapUtilities: true,
  },
  variants: {
    extend: {
      textColor: ['visited'],
      opacity: ['disabled'],
      cursor: ['disabled'],
    }
  },
  theme: {
    extend: {
      colors: {
        lime: colors.lime
      }
    }
  }
};