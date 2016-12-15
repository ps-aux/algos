const validate = require('webpack-validator')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path')


const config = {
    entry: "./src/js/main.js",
    output: {
        path: path.resolve(__dirname, 'build'),
        filename: "bundle.js"
    },

    module: {
        loaders: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            },
            {
                test: /\.less$/,
                exclude: /node_modules/,
                loader: ExtractTextPlugin.extract('style', 'css!less-loader')
            },
            {
                test: /\.scss$/,
                exclude: /node_modules/,
                loader: ExtractTextPlugin.extract('style', 'css?sourceMap!sass-loader?sourceMap')
            },
            // Bcs of bootstrap
            {
                test: /\.woff$/,
                loader: "url-loader?limit=10000&mimetype=application/font-woff&name=[path][name].[ext]"
            }, {
                test: /\.woff2$/,
                loader: "url-loader?limit=10000&mimetype=application/font-woff2&name=[path][name].[ext]"
            }, {
                test: /\.(eot|ttf|svg|gif|png)$/,
                loader: "file-loader"
            }
        ]

    },

    plugins: [
        // Output extracted CSS to a file
        new ExtractTextPlugin('style.css'),
        new HtmlWebpackPlugin({
            template: '!!pug!src/html/index.pug'
        }),
        new HtmlWebpackPlugin({
            filename: 'sorting.html',
            template: '!!pug!src/html/sorting.pug'
        })
    ],

    devServer: {
        inline: true
    },
    // Cannot be eval-source-map bcs of sass
    devtool: 'source-map'
}

module.exports = validate(config)
