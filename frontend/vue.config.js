const { defineConfig } = require('@vue/cli-service')
const NodePolyfillPlugin = require("node-polyfill-webpack-plugin")
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  configureWebpack:{
    plugins: [new NodePolyfillPlugin()],
    optimization:{
      splitChunks:{
        chunks: "all",
      },
    },
  },
  chainWebpack: (config) =>{
    config.plugin('define').tap((args) =>{
      args[0]['__VUE_PROD_HYDRATION_MISMATCH_DETAILS__'] = false
      return args
    })
  }
})
