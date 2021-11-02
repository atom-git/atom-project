import { mapGetters } from 'vuex'
/*
 * 默认配置的混入
 */
export default {
  data () {
    return {
      // 是否生产环境
      isProd: process.env.NODE_ENV === 'production'
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
    // 布局主题颜色 dark color 均对应dark, light对应light
    colorTheme () {
      return this.appConfig.theme === 'light' ? 'light' : 'dark'
    },
    // 内容部分主题色
    contentTheme () {
      return this.appConfig.theme === 'dark' ? 'dark' : 'light'
    },
    // 可视区域样式
    contentStyle () {
      return { height: this.appConfig.layout === 'top' || this.appConfig.layout === 'drawer' ? this.contentHeight + 'px' : '100%' }
    },
    // 静态资源路径
    publicPath () {
      return this.isProd ? '/html/dashboard/' : '/'
    }
  }
}
