const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const webpackMerge = require('webpack-merge');
const devConfig = require('./webpack.dev.config.js');
const target = process.env.npm_lifecycle_event;

const common = {
    entry: {
        bundle: path.join(__dirname + '/src/main/resources/static/entry.js')
    },
    output: {
        path: path.join(__dirname, '/src/main/resources/static'),
        filename: 'bundle.js'
    },
    node: {
        net: 'empty',
        tls: 'empty'
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                loader: 'babel',
                exclude: /node_modules/,
                query: {
                    presets: ['es2015']
                }
            },
            {
                test: /\.css$/,
                loader: "style-loader!css-loader"
            },
            {
                test: /\.(gif|png|jpg)$/,
                loader: 'file-loader?name=images/[name].[ext]&mimeType=image/[ext]&limit=100000'
            }
        ]
    },
    plugin: [
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false
            }
        }),
        new webpack.NoErrorsPlugin(),
        new webpack.optimize.OccurrenceOrderPlugin()
    ]
}


const prodConfig = {
    plugins: [
        new webpack.optimize.UglifyJsPlugin({
            compress: {warnings: false}
        }),
        new ExtractTextPlugin("[name].css")
    ]
}

var config;
if(target === 'prod') {
    console.log('real build');
    config = webpackMerge(common, prodConfig);
} else {
    console.log('dev build');
    config = webpackMerge(common, devConfig);
}

module.exports = config;