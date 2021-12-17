// 全局的样式覆盖
const path = require('path')
const MomentLocalesPlugin = require('moment-locales-webpack-plugin')
const AntDesignThemePlugin = require('antd-theme-webpack-plugin')
const { themeVars } = require('./theme')

// 路径解析替换
function resolve (dir) {
  return path.join(__dirname, dir)
}

// 主题切换配置options
const themeOptions = {
  antDir: resolve('./node_modules/ant-design-vue'),
  stylesDir: resolve('./src'),
  varFile: resolve('./src/config/theme/default.less'),
  themeVariables: themeVars,
  outputFilePath: resolve('./public/theme/color.less'),
  generateOnce: true
}

module.exports = {
  // 设置打包输出位置
  outputDir: '../server/src/main/resources/dashboard',
  // 配置应用根路由，这里必须和dashboard后台跟路径分开，防止静态资源被过滤
  publicPath: process.env.NODE_ENV === 'production' ? '/html/dashboard/' : '/',
  chainWebpack: (config) => {
    // 设置路径别称
    config.resolve.alias.set('@', resolve('./src'))
  },
  configureWebpack: {
    plugins: [
      // 主题色替换器
      new AntDesignThemePlugin(themeOptions),
      // 或者：剥离除 “en”、“es-us” 和 “ru” 以外的所有语言环境 （“en” 内置于 Moment 中，无法移除），不能使用new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/)的方式，会导致picker中文初始化异常
      new MomentLocalesPlugin({
        localesToKeep: ['es-us', 'zh-cn']
      })
    ],
    // 优化包大小，分离第三方大插件
    optimization: {
      splitChunks: {
        chunks: 'all',
        automaticNameDelimiter: '~',
        name: true,
        cacheGroups: {
          // 第三方库抽离
          vendor: {
            name: "modules",
            test: /[\\/]node_modules[\\/]/,
            priority: -10
          },
          // 图标库
          anticons: {
            name: 'anticons',
            test: /[\\/]node_modules[\\/]@ant-design[\\/]icons-vue[\\/]/
          },
          // 富文本库
          tinymce: {
            name: "tinymce",
            test: /[\\/]node_modules[\\/]tinymce[\\/]/
          }
        }
      }
    }
  },
  // 生产环境禁用sourceMap
  productionSourceMap: false,
  // 是否在开发环境下通过 eslint-loader 在每次保存时 lint 代码
  lintOnSave: process.env.NODE_ENV !== 'production',
  pluginOptions: {
    'style-resources-loader': {
      preProcessor: 'less',
      patterns: [
        // 这里是指所有需要预解析的文件夹目录
        resolve('./src/config/theme/default.less')
      ]
    }
  },
  css: {
    loaderOptions: {
      less: {
        /* less 变量覆盖，用于自定义 ant design 主题 */
        modifyVars: {
          'card-head-padding': '12px',
          'card-padding-base': '16px',
          'modal-body-padding': '16px',
          'drawer-body-padding': '16px'
        },
        javascriptEnabled: true
      }
    }
  },
  // app运行配置
  devServer: {
    port: 9099,
    proxy: {
      '/login': {
        target: 'https://localhost:8443'
      },
      '/api': {
        target: 'https://localhost:8443'
      },
      '/stomp': {
        target: 'https://localhost:8443',
        ws: true
      }
    }
  },
  // 实时解析
  runtimeCompiler: true
}
