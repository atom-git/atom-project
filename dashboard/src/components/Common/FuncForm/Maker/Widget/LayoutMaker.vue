<template>
  <!-- 标题布局 -->
  <FuncTitle v-if="isType('title')" :title="widget.options.default"></FuncTitle>
  <!-- 文本链接 -->
  <a v-else-if="isType('link')"
     :href="widget.options.href || ''"
     target="_blank">{{ widget.options.content || '外部链接' }}</a>
  <!-- html -->
  <div v-else-if="isType('html')"
       v-html="widget.options.content || 'html内容'"></div>
  <!-- 默认为文本布局 -->
  <div v-else>{{ widget.options.default }}</div>
</template>

<script>
/**
 * 布局构造器
 */
import FuncTitle from '@/components/Common/FuncTitle'
export default {
  name: 'LayoutMaker',
  components: { FuncTitle },
  props: {
    // 组件
    widget: {
      type: Object,
      required: true
    }
  },
  // 禁用继承
  inheritAttrs: false,
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.widget.type || 'text')
    }
  }
}
</script>
