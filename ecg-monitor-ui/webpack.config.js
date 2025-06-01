const path = require('path');

module.exports = {
    entry: './src/index.js',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js',
        publicPath: '/',
        clean: true,
    },
    mode: process.env.NODE_ENV || 'production',
    devtool: 'source-map',
    module: {
        rules: [],
    },
    optimization: {
        minimize: false,
        removeAvailableModules: false,
        removeEmptyChunks: false,
        splitChunks: false,
    },
    devServer: {
        static: {
            directory: path.join(__dirname, 'dist'),
        },
        hot: true,
        port: 3000,
        open: true,
        devMiddleware: {
            writeToDisk: true,
        },
    },
};
