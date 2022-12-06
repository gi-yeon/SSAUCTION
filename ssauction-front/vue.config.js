const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  publicPath: "",
  // devServer: {
  //   port: 8083,
  //   proxy: "http://localhost:8080",
  // },
  transpileDependencies: true,
  lintOnSave: false,
});
