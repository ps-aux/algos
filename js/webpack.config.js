// const ExtractTextPlugin = require('extract-text-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path')


const config = {
    resolve: {
        modules: ['node_modules', path.resolve(__dirname)]
    },
    output: {
        publicPath: '/'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            },
            {
                test: /\.sass$/,
                exclude: [/node_modules/, /\.global.sass$/],
                loaders: ['style-loader',
                    {
                        loader: 'css-loader',
                        options: {
                            modules: true
                        }
                    },
                    {
                        loader: 'sass-loader',
                        options: {
                            data: '@import "src/style/imports"'
                        }
                    }]
            },
            {
                test: /\.global.sass$/,
                loaders: ['style-loader', 'css-loader',
                    {
                        loader: 'sass-loader',
                        options: {
                            data: '@import "src/style/imports"'
                        }
                    }]
            },
            {
                test: /\.css$/,
                loaders: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|svg|jpg|gif|eot|ttf|woff|woff2)$/,
                loader: 'file-loader'
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: 'public/index.html'
        })
    ],
    // devtool: 'inline-source-map',
    devServer: {
        historyApiFallback: true
    }
}

module.exports = config
