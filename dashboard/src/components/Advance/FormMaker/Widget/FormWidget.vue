<template>
  <a-form-item :key="widget.key"
               :name="widget.key"
               :labelCol="labelCol"
               :label="widget.options.labelVisible ? widget.options.label : ''">
    <!-- 文本链接 -->
    <a v-if="isType('link')"
       :href="widget.options.href || ''"
       target="_blank">{{ widget.options.content || '外部链接' }}</a>
    <!-- html -->
    <div v-else-if="isType('html')"
         v-html="widget.options.content || 'html内容'"></div>
    <!-- 文本布局 -->
    <div v-else-if="isType('text')">{{ widget.options.default }}</div>
    <!-- form组件元素 -->
    <FieldRender v-else
                 :field="widget.options"
                 :modelValue="widget.options.default"
                 :size="size"></FieldRender>
  </a-form-item>
</template>

<script>
/**
 * 表单组件
 */
import { FieldRender } from '@/components/Common/Render'
export default {
  name: 'FormWidget',
  components: { FieldRender },
  props: {
    // 组件
    widget: {
      type: Object,
      default: () => ({})
    },
    // label的展示形式
    labelCol: {
      type: Object,
      required: false
    },
    // 组件尺寸
    size: {
      type: String,
      default: 'default'
    }
  },
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.widget.type || 'text')
    }
  }
}
</script>
