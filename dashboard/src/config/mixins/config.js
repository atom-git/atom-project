import { mapGetters } from 'vuex'
/*
 * 默认配置的混入
 */
export default {
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
    }
  }
}
