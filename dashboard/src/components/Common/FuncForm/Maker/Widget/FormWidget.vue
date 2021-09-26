<template>
  <a-form-item :key="widget.key"
               :class="[active ? 'active' : '']"
               :name="widget.key"
               :label="widget.options.labelVisible ? widget.options.label : ''">
    <!-- form组件元素 -->
    <FieldRender :field="widget.options"
                 :modelValue="widget.options.default"
                 :size="size"></FieldRender>
  </a-form-item>
</template>

<script>
/**
 * 表单组件
 */
import FieldRender from '../../Form/Render/FieldRender'
export default {
  name: 'FormWidget',
  components: { FieldRender },
  props: {
    // 组件
    widget: {
      type: Object,
      default: () => ({})
    },
    // 组件尺寸
    size: {
      type: String,
      default: 'default'
    },
    // 是否当前选中
    active: {
      type: Boolean,
      default: false
    }
  }
}
</script>
