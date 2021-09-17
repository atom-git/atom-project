<template>
  <a-tabs v-model:activeKey="activeTab">
    <a-tab-pane key="field" tab="组件属性">
      <!-- 组件配置 -->
      <FieldConfig></FieldConfig>
    </a-tab-pane>
    <a-tab-pane key="form" tab="表单属性">
      <!-- 表单配置 -->
      <FormConfig></FormConfig>
    </a-tab-pane>
  </a-tabs>
</template>

<script>
/**
 * 配置面板
 */
import FieldConfig from '../Widget/FieldConfig'
import FormConfig from '../Widget/FormConfig'
export default {
  name: 'ConfigPanel',
  components: { FieldConfig, FormConfig },
  props: {

  },
  data () {
    return {
      // 当前激活的tab
      activeTab: 'field'
    }
  }
}
</script>
