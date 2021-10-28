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
import reconfig from './mixins/reconfig'
export default {
  name: 'FormMaker',
  components: { WidgetPanel, MakerPanel, ConfigPanel },
  mixins: [config, reconfig],
  data () {
    return {
      // 表单编辑器的配置
      makerConfig: {},
      // 当前操作的组件
      curWidget: {},
      // 涉及options的组件，此类组件不需要设置default属性
      optionsWidgets: ['select', 'radio', 'checkbox', 'cascader', 'treeSelect', 'transfer', 'tagCheck']
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
        name: this.curWidget.key,
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
      // 组件无法直接挂载的参数调整
      this.widgetReconfig(this.curWidget, widgetConfig)
    }
  }
}
</script>

<style lang="less">
@import './Style/formMaker';
</style>
