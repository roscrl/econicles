// npx tailwindcss@latest -o views/assets/tailwind.css --watch

module.exports = {
    mode: 'jit',
    content: [
        './views/**/*.jte',
    ],
    theme: {
        screens: {
            'betterhover': {'raw': '(hover: hover)'},

            'sm': '640px',
            // => @media (min-width: 640px) { ... }

            'md': '768px',
            // => @media (min-width: 1024px) { ... }

            'lg': '1024px',
            'xl': '1280px',
            '2xl': '1536px',
            '3xl': '1792px',
            '4xl': '2048px'
        },
        extend: {
            gridTemplateColumns: {
                '7': 'repeat(7, minmax(0, 1fr))',
                '8': 'repeat(8, minmax(0, 1fr))',
                '9': 'repeat(9, minmax(0, 1fr))',
                '10': 'repeat(10, minmax(0, 1fr))',
                '11': 'repeat(11, minmax(0, 1fr))',
                '12': 'repeat(12, minmax(0, 1fr))',
                '13': 'repeat(13, minmax(0, 1fr))',
                '14': 'repeat(14, minmax(0, 1fr))',
                '15': 'repeat(15, minmax(0, 1fr))',
                '16': 'repeat(16, minmax(0, 1fr))',
            },
            gridColumnEnd: {
                '13': '13',
                '14': '14',
                '15': '15',
                '16': '16',
            },

        }
    }
}
