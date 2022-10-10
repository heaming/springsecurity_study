const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

module.exports = {
  outputDir: "../src/main/resources/static",
  // build: {
  //   index: "../src/main/resources/templates/index.html",
  // },
  devServer: {
    proxy: "http://localhost:8085",
    changeOrigin: true,
  },
}
