const webpack = require('webpack');

const config = require('./webpack.config');
const webpackMerge = require('webpack-merge');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const DefinePlugin = require('webpack/lib/DefinePlugin');

module.exports = webpackMerge(config, {
  devtool: '#source-map',

  module: {
    rules: [
      {
        test: /\.scss$/,

        use: ExtractTextPlugin.extract({
          fallback: 'style-loader',
          //resolve-url-loader may be chained before sass-loader if necessary
          use: ['css-loader', 'sass-loader']
        }),

        exclude: /(node_modules)/
      },

      {
        test: /\.css$/,
        use: [
    //{ loader: "style-loader"},
    { loader: "css-loader"}
  ],
        exclude: /(node_modules)/
      }

      ,
      { test: /\.(png|woff|woff2|eot|ttf|svg)$/, loader: 'url-loader?limit=100000' }
    ]
  },
  plugins: [
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        warnings: false
      }
    }),

    new DefinePlugin({
      ENV: JSON.stringify('production')
    }),

    new ExtractTextPlugin('[name].[chunkhash].css')
  ]
});
