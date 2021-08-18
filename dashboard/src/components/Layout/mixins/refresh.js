/**
 * 路由refresh相关配置
 */
export default {
  data () {
    return {
      // 子组件是否存活，用于子组件reload刷新
      isAlive: true
    }
  },
  // 注入reload方法
  provide () {
    return {
      reload: this.reload
    }
  },
  methods: {
    // 子组件刷新
    reload () {
      this.$nextTick(() => {
        this.isAlive = true
      }).then(() => {})
    }
  }
}
