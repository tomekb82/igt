const helpers = require('./helpers');
const webpack = require('webpack');
const validator = require('webpack-validator');
const ClearWebpackPlugin = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  entry: {
    app: './src/app/app.js',
    vendor: './src/vendor.js'
  },

  output: {
    filename: '[name].[chunkhash].js',
    path:  helpers.absolutePath('dist')
  },

  module: {
    rules: [
     {
        enforce: "pre",
        test: /\.js$/,
        exclude: /(node_modules)/,
        loader: "eslint-loader",
        options: {
	    configFile: './config/eslintrc.json'
         }  
      },
     {
    		test: /\.js$/,
    		use: [
			{ loader:'ng-annotate-loader'},
			{ loader:'babel-loader?presets=es2015'}
		],
    		exclude: /(node_modules)/
      },

      {
        test: /\.html$/,
        loader: 'raw-loader',
        exclude: [helpers.absolutePath('src/index.html')]
      },





  	]
  },


  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      inject: 'body'
    }),

    new webpack.optimize.CommonsChunkPlugin({
      name: ['vendor', 'manifest']
    }),

    new ClearWebpackPlugin(['dist'], {
      root: helpers.absolutePath(''),
      verbose: true
    })
  ],

  devServer: {
    port: 3000,
    host: 'localhost',

    historyApiFallback: true,
    watchOptions: {
      aggregateTimeout: 300
    },

    stats: 'errors-only'
  }

};
