<template>
  <FieldRender :field="field"></FieldRender>
</template>

<script>
/**
 * 表单组件构造器
 */
import FieldRender from '../../Form/Render/FieldRender'
export default {
  name: 'WidgetMaker',
  components: { FieldRender },
  props: {
    field: {
      type: Object,
      default: () => ({})
    },
    size: {
      type: String,
      default: 'default'
    }
  },
  // 防止v-bind绑定时继承多个onChange等异常现象
  inheritAttrs: false
}
</script>
