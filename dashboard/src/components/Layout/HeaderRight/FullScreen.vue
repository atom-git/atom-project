<template>
  <a-tooltip placement="bottom"
             :title="fullScreen ? $t('Layout.HeaderRight.FullScreen.exitFull') : $t('Layout.HeaderRight.FullScreen.full')">
    <span class="atom-header-button">
      <IconFont :type="fullScreen ? 'CompressOutlined' : 'ExpandOutlined'" @click="toggleScreen"/>
    </span>
  </a-tooltip>
</template>

<script>
export default {
  name: 'FullScreen',
  data () {
    return {
      fullScreen: false
    }
  },
  methods: {
    // 切换全屏与非全屏
    toggleScreen () {
      const body = document.body
      if (this.fullScreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen()
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen()
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen()
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen()
        }
      } else {
        if (body.requestFullscreen) {
          body.requestFullscreen()
        } else if (body.mozRequestFullScreen) {
          body.mozRequestFullScreen()
        } else if (body.webkitRequestFullScreen) {
          body.webkitRequestFullScreen()
        } else if (body.msRequestFullscreen) {
          body.msRequestFullscreen()
        }
      }
      // 必须放在后面，否则会有document not active错误
      this.fullScreen = !this.fullScreen
    }
  }
}
</script>
