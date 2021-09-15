<template>
  <a-layout>
    <!-- 左侧组件面板区域 -->
    <a-layout-sider>
      <WidgetPanel></WidgetPanel>
    </a-layout-sider>
    <!-- 中间画布面板区域 -->
    <a-layout>
      <a-layout-header>Header</a-layout-header>
      <a-layout-content>
        <MakerPanel></MakerPanel>
      </a-layout-content>
    </a-layout>
    <!-- 右侧配置面板区域 -->
    <a-layout-sider>
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
export default {
  name: 'FormMaker',
  components: { WidgetPanel, MakerPanel, ConfigPanel }
}
</script>
