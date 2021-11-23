<template>
  <a-tabs v-model:activeKey="activeTab">
    <a-tab-pane key="field" tab="组件属性">
      <!-- 组件配置 -->
      <WidgetConfig ref="widgetConfig"
                    v-model="innerWidgetConfig"
                    :fields="fields"
                    @change="handleWidgetChange"></WidgetConfig>
    </a-tab-pane>
    <a-tab-pane key="form" tab="表单属性" :forceRender="true">
      <!-- 表单配置 -->
      <FormConfig ref="formConfig"
                  v-model="innerFormConfig"
                  @change="handleFormChange"></FormConfig>
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
    // 表单配置
    formConfig: {
      type: Object
    },
    // 组件配置
    widgetConfig: {
      type: Object
    },
    // 组件配置字段
    fields: {
      type: Array,
      required: false
    }
  },
  data () {
    return {
      // 当前激活的tab
      activeTab: 'field',
      // form表单配置
      innerFormConfig: {},
      // 组件配置
      innerWidgetConfig: {}
    }
  },
  watch: {
    // 监听外部传入值的变化
    formConfig: {
      deep: true,
      handler (newValue) {
        this.innerFormConfig = newValue || {}
      }
    },
    // 监听外部传入的组件值变化
    widgetConfig: {
      deep: true,
      handler (newValue) {
        this.innerWidgetConfig = newValue || {}
      }
    },
    // 表单配置的双绑
    innerFormConfig: {
      deep: true,
      handler (newValue) {
        this.$emit('update:formConfig', newValue)
      }
    }
  },
  emits: ['update:formConfig', 'form-config-change', 'widget-config-change'],
  methods: {
    // 响应form表单配置变更
    handleFormChange (formConfig) {
      this.$emit('form-config-change', formConfig)
    },
    // 响应组件配置变更
    handleWidgetChange (widgetConfig) {
      this.$emit('widget-config-change', widgetConfig)
    },
    // 触发FormConfig的校验
    triggerFormConfigValidate () {
      return this.$refs.formConfig.$refs.formList.validate()
    },
    // 失败时外部触发至form表单配置处
    toggleToFormConfig () {
      this.activeTab = 'form'
    }
  }
}
</script>
