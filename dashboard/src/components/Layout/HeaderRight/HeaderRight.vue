<template>
  <div class="atom-header-right">
    <News></News>
    <FullScreen v-if="showFullScreen && !device.isMobile && clientWidth > 375"></FullScreen>
    <ThemeSetup v-if="!device.isMobile && clientWidth > 375"></ThemeSetup>
    <UserMenu></UserMenu>
  </div>
</template>

<script>
import News from './News'
import UserMenu from './UserMenu'
import FullScreen from './FullScreen'
import ThemeSetup from './ThemeSetup'
import { mapGetters } from 'vuex'
export default {
  name: 'HeaderRight',
  components: {
    News,
    UserMenu,
    FullScreen,
    ThemeSetup
  },
  computed: {
    ...mapGetters(['device', 'clientWidth'])
  },
  data () {
    return {
      // 是否显示全屏
      showFullScreen: window.navigator.userAgent.indexOf('MSIE') < 0
    }
  }
}
</script>

<style lang="less">
@import "HeaderRight";
</style>
