<template>
  <a-layout class="atom-form-maker" :style="formMakerStyle">
    <!-- 左侧组件面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-widget-panel">
      <WidgetPanel></WidgetPanel>
    </a-layout-sider>
    <!-- 中间画布面板区域 -->
    <a-layout class="atom-maker-panel">
      <a-layout-header>Header</a-layout-header>
      <a-layout-content>
        <MakerPanel></MakerPanel>
      </a-layout-content>
    </a-layout>
    <!-- 右侧配置面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-config-panel">
      <ConfigPanel></ConfigPanel>
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
  computed: {
    // 可视区域样式
    formMakerStyle () {
      return { height: this.appConfig.layout === 'top' || this.appConfig.layout === 'drawer' ? this.contentHeight + 'px' : '100%' }
    }
  }
}
</script>

<style lang="less">
@import './Style/formMaker';
</style>
