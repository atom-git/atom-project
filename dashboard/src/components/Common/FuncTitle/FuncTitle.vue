<template>
  <span v-if="title" :class="['atom-title', prefix ? 'atom-has-prefix' : '']">
    <IconFont v-if="prefix" :type="prefix"/>
    {{ title }}
  </span>
</template>

<script>
/**
 * 功能表标题，统一展现样式
 */
export default {
  name: 'FuncTitle',
  props: {
    // 自定义标题
    title: {
      type: String,
      default: '自定义标题'
    },
    // 标题前缀, 传入icon
    prefix: {
      type: String,
      default: ''
    },
    // 用于功能性跳转，比如点击企业名字后直接调转至企业详情
    route: {
      type: String,
      required: false
    },
    // 跳转传递的参数
    params: {
      type: Object,
      default: () => ({})
    }
  },
  methods: {
    // 响应点击
    handleClick () {
      if (this.route) {
        const routeUrl = this.$router.resolve({ name: this.route, params: this.params })
        window.open(routeUrl.href, '_blank')
      }
    }
  }
}
</script>

<style lang="less">
@import "title";
</style>
