<template>
  <a-layout-content class="atom-maker-panel">
    <!-- 画布头部 -->
    <MakerHeader @maker-header-action="handleHeaderAction"
                 @maker-canvas-resize="handleCanvasResize"></MakerHeader>
    <div class="atom-maker-canvas-panel">
      <div :class="['atom-maker-canvas', panel]">

      </div>
    </div>
  </a-layout-content>
</template>

<script>
/**
 * 画布面板
 */
import MakerHeader from '../Widget/MakerHeader'
export default {
  name: 'MakerPanel',
  components: { MakerHeader },
  data () {
    return {
      // 画布的样式 mac | pad | phone
      panel: 'mac'
    }
  },
  methods: {
    // 响应头部点击响应
    handleHeaderAction (action) {
      console.log(action)
    },
    // 响应画布大小调整
    handleCanvasResize (panel) {
      this.panel = panel
    }
  }
}
</script>
