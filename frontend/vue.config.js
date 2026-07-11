const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8082,
    proxy: {
      '/static': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws: false
      },
      '/uploads': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws: false
      }
    }
  }
})
