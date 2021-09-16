<template>
  <a-layout class="atom-form-maker">
    <!-- 左侧组件面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-widget-panel" :width="280">
      <WidgetPanel></WidgetPanel>
    </a-layout-sider>
    <!-- 中间画布面板区域 -->
    <MakerPanel></MakerPanel>
    <!-- 右侧配置面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-config-panel" :width="280">
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
  mixins: [config]
}
</script>

<style lang="less">
@import './Style/formMaker';
</style>
