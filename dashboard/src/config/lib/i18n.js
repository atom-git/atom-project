/**
 * 国际化
 */
import { createI18n } from 'vue-i18n'
import { store } from '@/store'
import zhCN from '@/config/langs/zh-CN'
import enUS from '@/config/langs/en-US'
import Default from '@/config/default'

/**
 * 语言库
 */
const messages = {
  'zh-CN': zhCN,
  'en-US': enUS
}

/**
 * 实例化i18n对象
 */
const i18n = createI18n({
  locale: store.getters.appConfig.locale,
  messages: messages
})

/**
 * 设置语言
 */
export function loadLanguage (locale = Default.locale) {
  return new Promise(resolve => {
    // 语言包不一样时切换语言包
    if (i18n.global.locale !== locale ) {
      // 设置自定义的国际化
      i18n.global.setLocaleMessage(locale, messages[locale])
      i18n.global.locale = locale
      resolve(locale)
    }
  })
}

export default i18n
