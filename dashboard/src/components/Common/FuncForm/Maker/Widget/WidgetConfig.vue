<template>
  <FormList v-if="fields && fields.length > 0"
            layout="vertical"
            v-model="widgetConfig"
            :fields="fields"
            hiddenFooter></FormList>
  <a-empty v-else description="请添加组件"/>
</template>

<script>
/**
 * 组件配置
 */
import { FormList } from '@/components/Common/FuncForm'
export default {
  name: 'WidgetConfig',
  components: { FormList },
  props: {
    // 双绑的组件配置
    modelValue: {
      type: Object
    },
    // 组件配置属性
    fields: {
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
