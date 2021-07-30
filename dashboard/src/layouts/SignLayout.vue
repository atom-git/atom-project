<template>
  <div :class="['atom-sign-layout', device.type]">
    <!-- 顶部语言选择和主题选择 -->
    <div class="atom-header-right">
      <Global></Global>
      <ThemeMode></ThemeMode>
    </div>
    <a-layout-content>
      <Logo title="Atom Design" size="large" desc="Atom Design 是最优美严谨的HTML5 UI框架"></Logo>
      <div class="atom-sign-content">
        <RouteView></RouteView>
      </div>
    </a-layout-content>
    <Footer></Footer>
  </div>
</template>

<script>
/**
 * 登录注册相关布局
 */
import RouteView from '@/components/Layout/RouteView'
import Global from '@/components/Layout/HeaderRight/Global'
import ThemeMode from '@/components/Layout/HeaderRight/ThemeMode'
import Logo from '@/components/Layout/Logo'
import Footer from '@/components/Layout/Footer'
import config from '@/config/mixins/config'
export default {
  name: 'SignLayout',
  components: { RouteView, Global, ThemeMode, Logo, Footer },
  mixins: [config]
}
</script>

<style lang="less">
@import "Style/sign";
</style>
