const path = require("path");
const HtmlWebPackPlugin = require("html-webpack-plugin"); // eslint-disable-line import/no-extraneous-dependencies
const CopyWebpackPlugin = require("copy-webpack-plugin");

const htmlPlugin = new HtmlWebPackPlugin({
  template: path.join(__dirname, "src/main/react/index.html"),
  filename: "./index.html"
});

module.exports = {
  entry: [
      // 'babel-polyfill',
      path.join(__dirname, "src/main/react")
  ],
  output: {
    path: path.resolve(__dirname, "static")
  },

  devtool: "cheap-module-source-map",
  module: {
    rules: [
      {
        test: /\.js|jsx$/,
        exclude: /node_modules\/(?!(@flock-eco)\/).*/,
        use: {
          loader: "babel-loader",
          options: {
            plugins: ["@babel/plugin-proposal-class-properties"],
            presets: ["@babel/preset-env", "@babel/preset-react"]
          }
        }
      },
      {
        test: /\.css$/i,
        // use: ["style-loader", "css-loader"]
      }
    ]
  },

  plugins: [
    htmlPlugin,
    new CopyWebpackPlugin([
        // "src/main/react/images"
      {from: "react/images", to: "images", context: "src/main" },
      // { from: "react/manifest.json", to: "webapp/", context: "src/main" },
      "src/main/react/manifest.json",
      // "src/main/react/eventSource.js"
    ])
  ],

  devServer: {
    historyApiFallback: true,
    // http2: true,
    port: 3000,
    proxy: {
      "/wordclouds": "http://localhost:8080",
      "/wordclouds/**": "http://localhost:8080"
    }
  }
};
