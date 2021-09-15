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
    // 布局主题颜色 dark color 均对应dark, light对应light
    colorTheme () {
      return this.appConfig.theme === 'light' ? 'light' : 'dark'
    },
    // 内容部分主题色
    contentTheme () {
      return this.appConfig.theme === 'dark' ? 'dark' : 'light'
    }
  }
}
