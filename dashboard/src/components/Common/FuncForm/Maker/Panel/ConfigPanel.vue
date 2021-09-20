<template>
  <a-tabs v-model:activeKey="activeTab">
    <a-tab-pane key="field" tab="组件属性">
      <!-- 组件配置 -->
      <WidgetConfig v-model="widgetConfig" @change="handleWidgetChange"></WidgetConfig>
    </a-tab-pane>
    <a-tab-pane key="form" tab="表单属性" :forceRender="true">
      <!-- 表单配置 -->
      <FormConfig v-model="formConfig" @change="handleFormChange"></FormConfig>
    </a-tab-pane>
  </a-tabs>
</template>

<script>
/**
 * 配置面板
 */
import WidgetConfig from '../Widget/WidgetConfig'
import FormConfig from '../Widget/FormConfig'
export default {
  name: 'ConfigPanel',
  components: { WidgetConfig, FormConfig },
  props: {
    // 双绑的配置值，包括组件配置及表单配置
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
      // 当前激活的tab
      activeTab: 'field',
      // 组件配置
      widgetConfig: {},
      // form表单配置
      formConfig: {}
    }
  },
  watch: {
    // 监听外部传入值的变化
    modelValue: {
      deep: true,
      handler (newValue) {
        this.widgetConfig = (newValue && newValue.widgetConfig) || {}
        this.formConfig = (newValue && newValue.formConfig) || {}
      }
    },
    // 组件配置的双绑
    widgetConfig: {
      deep: true,
      handler (newValue) {
        const modelValue = { widgetConfig: newValue, formConfig : this.formConfig }
        this.$emit('update:modelValue', modelValue)
        this.$emit('change', modelValue)
      }
    },
    // form配置的双绑
    formConfig: {
      deep: true,
      handler (newValue) {
        const modelValue = { widgetConfig: this.widgetConfig, formConfig : newValue }
        this.$emit('update:modelValue', modelValue)
        this.$emit('change', modelValue)
      }
    }
  },
  emits: ['update:modelValue', 'change', 'form-config-change', 'widget-config-change'],
  methods: {
    // 响应form表单配置变更
    handleFormChange (formConfig) {
      this.$emit('form-config-change', formConfig)
    },
    // 响应组件配置变更
    handleWidgetChange (widgetConfig) {
      this.$emit('widget-config-change', widgetConfig)
    }
  }
}
</script>
