/* Common Config */
const Merge = require('webpack-merge');
const CommonConfig = require('./webpack.common.config');

module.exports = Merge.smart(CommonConfig, {
    entry: {
        examples: "./ts/examples/App.tsx",
        vendor: ["babel-polyfill", "react", "react-dom"]
    },
});