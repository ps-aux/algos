module.exports = {
    extends: ['eslint-config-standard', 'plugin:react/recommended'],
    plugins: ['prettier', 'react'],
    parser: 'babel-eslint',
    rules: {
        curly: 'off',
        'space-before-function-paren': 'off',
        'prettier/prettier': 'error',
        // Prettier conflict
        'standard/computed-property-even-spacing': 'off',
        'promise/param-names': 'off',
        indent: 'off',
        // So we can use comma operator
        'no-sequences': 'off',
        'react/prop-types': 'off',
        'react/display-name': 'off'
    },
    overrides: [
        {
            files: ['*.test.js', '*.spec.js'],

            globals: {
                it: true,
                expect: true,
                beforeEach: true,
                describe: true
            }
        }
    ],

    settings: {
        react: {
            version: 'detect' // React version. "detect" automatically picks the version you have installed.
        }
    }
}
