const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const webpackMerge = require('webpack-merge');
const devConfig = require('./webpack.dev.config.js');
const target = process.env.npm_lifecycle_event;
const STATIC_PATH = '/src/main/resources/static';

const common = {
    entry: {
        bundle: path.join(__dirname, STATIC_PATH, '/entry.js')
    },

    output: {
        path: path.join(__dirname, STATIC_PATH, 'dist'),
        filename: 'bundle.js'
    },

    devtool: 'eval-source-map',

    devServer: {
        hot: true,
        inline: true,
        compress: true,
        contentBase: path.join(__dirname, STATIC_PATH, '/dist/'),
    },

    node: {
        net: 'empty',
        tls: 'empty'
    },

    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/,
            include: path.join(__dirname, STATIC_PATH, 'js'),
            use: [{
                loader: 'babel-loader',
            }]
        }, {
            test: /\.css$/,
            use: ExtractTextPlugin.extract({
                fallback: 'style-loader',
                use: 'css-loader'
            })
        }]
    },
    plugins: [
        new webpack.NoEmitOnErrorsPlugin(),
        new webpack.optimize.OccurrenceOrderPlugin(),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'commons',
            filename: 'commons.js',
            minChunks: Infinity
        }),
        new webpack.HotModuleReplacementPlugin()
    ]
};

const prodConfig = {
    plugins: [
        new webpack.optimize.UglifyJsPlugin({
            compress: {warnings: false}
        }),
        new ExtractTextPlugin('[name].css')
    ]
}

let config;
if(target === 'prod') {
    console.log('real build');
    config = webpackMerge(common, prodConfig);
} else {
    console.log('dev build');
    config = webpackMerge(common, devConfig);
}

module.exports = config;
