<template>
  <div class="atom-header-right">
    <NoticeIcon></NoticeIcon>
    <Global></Global>
    <FullScreen v-if="showFullScreen && !device.isMobile"></FullScreen>
    <ThemeSetup v-if="!device.isMobile"></ThemeSetup>
    <UserMenu></UserMenu>
  </div>
</template>

<script>
import NoticeIcon from './NoticeIcon'
import UserMenu from './UserMenu'
import Global from './Global'
import FullScreen from './FullScreen'
import ThemeSetup from './ThemeSetup'
import { mapGetters } from 'vuex'
export default {
  name: 'HeaderRight',
  components: {
    NoticeIcon,
    UserMenu,
    Global,
    FullScreen,
    ThemeSetup
  },
  computed: {
    ...mapGetters(['device'])
  },
  data () {
    return {
      showFullScreen: window.navigator.userAgent.indexOf('MSIE') < 0
    }
  }
}
</script>

<style lang="less">
@import "headerRight";
</style>
