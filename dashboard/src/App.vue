<template>
  <a-config-provider :locale="locale">
    <router-view/>
  </a-config-provider>
</template>

<script>
import zhCN from 'ant-design-vue/es/locale-provider/zh_CN'
import { mapGetters } from 'vuex'
import Default from '@/config/default'
export default {
  data () {
    return {
      locale: zhCN
    }
  },
  computed: {
    ...mapGetters([
      'device',
      'scrollTop',
      'clientWidth',
      'clientHeight',
      'userInfo'
    ])
  },
  mounted () {
    const self = this
    /**
     * 监听滚动条
     */
    window.addEventListener('scroll', () => {
      self.$store.dispatch('setScrollTop', window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop)
    })
    /**
     * 响应窗体高度变化，初始化时要设置好
     */
    self.$store.dispatch('setClientWidth', document.body.clientWidth)
    self.$store.dispatch('setClientHeight', document.body.clientHeight)
    window.addEventListener('resize', () => {
      self.$store.dispatch('setClientWidth', document.body.clientWidth)
      self.$store.dispatch('setClientHeight', document.body.clientHeight)
      // 窗体大小变化时，布局的实时响应变化
      // 桌面模式必须大于768中断点宽度，在切换应该恢复成用户选中的布局，而非默认布局
      self.$store.dispatch('setLayout', self.device.isMobile || document.body.clientWidth < 768 ? Default.mobileLayout : self.userInfo.layout || Default.layout)
    })
  }
}
</script>
