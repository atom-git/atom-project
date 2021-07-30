/**
 * 主题工具类
 */
import darkVars from '@/../public/theme/dark.json'
import defaultVars from '@/../public/theme/default.json'
import Default from '@/config/default'

/**
 * 切换主题及主题色
 * @param theme 主题，light,mix统一为default， dark为dark
 * @param primaryColor 主题色
 */
export function toggleTheme (theme = 'default', primaryColor = Default.primaryColor) {
  const themeVars = {}
  if (theme === 'dark') {
    Object.assign(themeVars, darkVars, { '@primary-color': primaryColor })
  } else {
    Object.assign(themeVars, defaultVars, { '@primary-color': primaryColor })
  }
  return new Promise(resolve => {
    window.less.modifyVars(themeVars)
    resolve()
  })
}
