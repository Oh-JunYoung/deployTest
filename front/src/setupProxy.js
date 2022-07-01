//setupProxy.js

const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app){
  app.use(
    createProxyMiddleware('/server', {
      target: 'http://localhost:8080',
      pathRewrite: {
        '^/server': ''
      },
      changeOrigin: true
    })
  )
};