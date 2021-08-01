/**
 * 主题切换动态生态dark.json,default.json配置文件
 */
const fs = require("fs")
const { getThemeVariables } = require('./node_modules/ant-design-vue/dist/theme')

// 获取样式变量
const defaultVars = getThemeVariables()
const darkVars = getThemeVariables({ dark: true })

// 删除hack
delete defaultVars['hack']
delete darkVars['hack']

// 写出样式配置
fs.writeFileSync('./public/theme/default.json', JSON.stringify(defaultVars));
fs.writeFileSync('./public/theme/dark.json', JSON.stringify(darkVars));

module.exports = {
  themeVars: [
    ...Object.keys(defaultVars),
    ...Object.keys(darkVars)
  ]
}
