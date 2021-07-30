/**
 * 主题切换动态生态dark.json,default.json配置文件
 */
const { getLessVars } = require('antd-theme-generator')
const fs = require("fs")
const defaultPath = './node_modules/ant-design-vue/lib/style/themes/default.less'
const darkPath = './node_modules/ant-design-vue/lib/style/themes/dark.less'

const defaultVars = getLessVars(defaultPath)
const darkVars = { ...defaultVars, ...getLessVars(darkPath)}

// 写出样式配置
fs.writeFileSync('./public/theme/default.json', JSON.stringify(defaultVars));
fs.writeFileSync('./public/theme/dark.json', JSON.stringify(darkVars));

module.exports = {
  themeVars: [
    ...Object.keys(defaultVars),
    ...Object.keys(darkVars)
  ]
}
