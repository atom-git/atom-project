<template>
  <a-layout class="atom-form-maker">
    <!-- 左侧组件面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-widget-panel" :width="280">
      <WidgetPanel></WidgetPanel>
    </a-layout-sider>
    <!-- 中间画布面板区域 -->
    <MakerPanel :makerConfig="makerConfig"
                :curWidget="curWidget"
                @maker-widget-change="handleWidgetChange" ></MakerPanel>
    <!-- 右侧配置面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-config-panel" :width="280">
      <ConfigPanel v-model="makerConfig"
                   :fields="configFields"
                   @widget-config-change="handleWidgetConfigChange"></ConfigPanel>
    </a-layout-sider>
  </a-layout>
</template>

<script>
/**
 * 动态表单制作
 */
import WidgetPanel from './Panel/WidgetPanel'
import MakerPanel from './Panel/MakerPanel'
import ConfigPanel from './Panel/ConfigPanel'
import config from '@/config/mixins/config'
export default {
  name: 'FormMaker',
  components: { WidgetPanel, MakerPanel, ConfigPanel },
  mixins: [config],
  data () {
    return {
      // 表单编辑器的配置
      makerConfig: {},
      // 当前操作的组件
      curWidget: {},
      // 涉及options的组件，此类组件不需要设置default属性
      optionsWidgets: ['select', 'radio', 'checkbox', 'cascader', 'treeSelect', 'transfer']
    }
  },
  computed: {
    // 配置字段
    configFields () {
      return this.curWidget.fields || []
    }
  },
  methods: {
    // 响应当前填加的组件改变
    handleWidgetChange (curWidget) {
      this.curWidget = curWidget
      // 回写组件配置
      this.makerConfig.widgetConfig = curWidget.widgetConfig || {}
    },
    // 响应组件的配置变化
    handleWidgetConfigChange (widgetConfig) {
      // 保存组件配置
      this.curWidget.widgetConfig = widgetConfig
      this.curWidget.options = {
        ...this.curWidget.options,
        ...widgetConfig,
        style: { width: `${widgetConfig.width || 100}%` || '100%' }
      }
      // select时对配置参数进行格式化
      if (widgetConfig.options) {
        // 带选项配置的需要回写其选项配置结果
        if (this.optionsWidgets.includes(this.curWidget.type)) {
          this.curWidget.options.options = widgetConfig.options.options
          this.curWidget.options.default = widgetConfig.options.default
        }
      }
      // 栅格布局的参数调整
      if (this.curWidget.type === 'grid') {
        // 根据列数，计算列宽
        const span = 24 / (widgetConfig['colCount'] || 2)
        // 变化的列数
        const colChange = widgetConfig['colCount'] - this.curWidget.columns.length
        // 如果列数增加，在最后面增加，并调整列宽
        if (colChange > 0) {
          // 调整列宽
          this.curWidget.columns.forEach(column => column.span = span)
          for (let index = 0; index < colChange; index++) {
            this.curWidget.columns.push({
              key: 'column_' + (this.curWidget.columns.length + index),
              order: this.curWidget.columns.length + index,
              span: span,
              widgets: []
            })
          }
        } else {
          // 如果列数减少，从最后面减，并调整列宽
          this.curWidget.columns.splice(widgetConfig['colCount'], Math.abs(colChange))
          this.curWidget.columns.forEach(column => column.span = span)
        }
      }
    }
  }
}
</script>

<style lang="less">
@import './Style/formMaker';
</style>
