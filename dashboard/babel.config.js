// 在生产环境去除console打印
const plugins = []
if (process.env.NODE_ENV === 'production') {
  plugins.push('transform-remove-console')
}
// lazy load ant design style`style: true` 会加载 less 文件
plugins.push(['import', {
  libraryName: 'ant-design-vue',
  libraryDirectory: 'es',
  style: true
}])

module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  plugins
}
