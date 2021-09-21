<template>
  <p>{{ widget }}</p>
  <p>{{ options }}</p>
</template>

<script>
/**
 * 布局构造器
 */
export default {
  name: 'LayoutMaker',
  props: {
    // 组件
    widget: {
      type: Object,
      required: true
    },
    // 组件配置信息
    options: {
      type: Object,
      default: () => ({})
    }
  },
  // 禁用继承
  inheritAttrs: false
}
</script>
