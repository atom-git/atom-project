<template>
  <div v-if="options"></div>
  <a-empty v-else description="请添加组件"/>
  <!-- table, grid等布局需要在这里增加slot的自定义，table增加一个按照图形来创建的样式 -->
</template>

<script>
/**
 * 组件配置
 */
export default {
  name: 'WidgetConfig',
  props: {
    // 双绑的组件配置
    modelValue: {
      type: Object
    },
    // 组件配置属性
    options: {
      type: Array,
      required: false
    }
  },
  data () {
    return {
      // 组件配置
      widgetConfig: {}
    }
  },
  watch: {
    // 监听外部传入值的变化
    modelValue: {
      deep: true,
      handler (newValue) {
        this.widgetConfig = newValue
      }
    },
    // 组件配置的双绑
    widgetConfig: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
        this.$emit('change', newValue)
      }
    }
  },
  emits: ['update:modelValue', 'change']
}
</script>
