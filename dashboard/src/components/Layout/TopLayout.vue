<template>
  <a-layout-header>
    <!-- logo -->
    <Logo :title="title"></Logo>
    <TopMenu :menus="menus" :theme="colorTheme" :replaceKeys="{ title: 'name', key: 'route' }"></TopMenu>
    <!-- 右侧功能按钮 -->
    <HeaderRight></HeaderRight>
  </a-layout-header>
  <a-layout-content :class="multiTab ? 'atom-has-multitab' : ''">
    <!-- 多标签栏 -->
    <MultiTab v-if="multiTab"></MultiTab>
    <RouteView v-if="isAlive"></RouteView>
  </a-layout-content>
  <!-- 底部footer -->
  <Footer></Footer>
</template>

<script>
import Logo from './Logo'
import { TopMenu } from './Menu'
import RouteView from './RouteView'
import config from '@/config/mixins/config'
import user from '@/config/mixins/user'
import HeaderRight from './HeaderRight'
import MultiTab from './MultiTab'
import Footer from './Footer'
import refresh from './mixins/refresh'
export default {
  name: 'TopLayout',
  components: {
    Logo,
    TopMenu,
    RouteView,
    HeaderRight,
    MultiTab,
    Footer
  },
  mixins: [config, user, refresh]
}
</script>

<style lang="less">
@import "./Style/top";
</style>
