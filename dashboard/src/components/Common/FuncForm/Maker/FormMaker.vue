<template>
  <a-layout class="atom-form-maker">
    <!-- 左侧组件面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-widget-panel" :width="280">
      <WidgetPanel></WidgetPanel>
    </a-layout-sider>
    <!-- 中间画布面板区域 -->
    <MakerPanel :makerConfig="makerConfig"
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
      curWidget: {}
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
      if (widgetConfig.options && (this.curWidget.type === 'select'
          || this.curWidget.type === 'radio' || this.curWidget.type === 'checkbox'
          || this.curWidget.type === 'cascader')) {
        // 在select mode改变时，改变其options配置中的multiple配置
        if (this.curWidget.type === 'select') {
          widgetConfig.options.multiple = widgetConfig.mode === 'multiple' || widgetConfig.mode === 'tag'
        }
        this.curWidget.options.options = widgetConfig.options.options
        this.curWidget.options.default = widgetConfig.options.default
      }
    }
  }
}
</script>

<style lang="less">
@import './Style/formMaker';
</style>
