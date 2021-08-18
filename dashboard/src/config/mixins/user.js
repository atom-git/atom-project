import { mapGetters } from 'vuex'
/*
 * 用户信息的混入
 */
export default {
  computed: {
    ...mapGetters([
      'token',
      'userInfo',
      'menus',
      'actions',
      'appConfig'
    ]),
    // 当前的菜单路由
    curMenu () {
      return this.$route.name
    },
    // 水印相关配置
    waterMark () {
      return {
        content: this.userInfo.name,
        enable: this.appConfig.waterMark,
        theme: this.appConfig.theme === 'dark' ? 'dark' : 'light'
      }
    }
  }
}
