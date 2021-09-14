import { mapGetters } from 'vuex'
/*
 * 默认配置的混入
 */
export default {
  data () {
    return {
      // 菜单刷新
      menuRefresh: false
    }
  },
  computed: {
    ...mapGetters([
      'title',
      'appConfig',
      'device',
      'scrollTop',
      'clientWidth',
      'clientHeight',
      'contentHeight'
    ]),
    // 主题颜色 dark mix 均对应dark, light对应light
    colorTheme () {
      return this.appConfig.theme === 'light' ? 'light' : 'dark'
    },
    // 菜单的替换keys
    menuReplaceKeys () {
      return { title: this.appConfig.locale === 'zh-CN' ? 'name' : 'englishName', key: 'route' }
    }
  }
}
