<template>
  <a-layout-sider class="atom-sider"
                  breakpoint="xl"
                  :theme="colorTheme"
                  :collapsedWidth="48"
                  v-model:collapsed="collapsed">
    <!-- logo -->
    <Logo :title="title" :collapsed="collapsed"></Logo>
    <!-- 菜单 -->
    <SideMenu :menus="menus" :theme="colorTheme" :replaceKeys="{ title: 'name', key: 'route' }"></SideMenu>
    <!-- 折叠弹出按钮 -->
    <SiderTrigger :theme="colorTheme" v-model:collapsed="collapsed"></SiderTrigger>
  </a-layout-sider>
  <a-layout class="atom-sider-content" :style="{ marginLeft: `${collapsed ? 48 : 200}px`}">
    <a-layout-header :style="{ width: `calc(100% - ${fixHeader ? collapsed ? 48 : 200 : 0}px)` }">
      <!-- 左侧折叠弹出按钮 -->
      <div class="atom-sider-trigger" @click="() => collapsed = !collapsed">
        <IconFont :type="collapsed ? 'MenuUnfoldOutlined' : 'MenuFoldOutlined'"></IconFont>
      </div>
      <div style="flex: 1 1 0;"></div>
      <!-- 右侧功能按钮 -->
      <HeaderRight></HeaderRight>
    </a-layout-header>
    <a-layout-content :class="multiTab ? 'atom-has-multitab' : ''">
      <!-- 多标签栏 -->
      <MultiTab v-if="multiTab" :style="{ width: `calc(100% - ${fixHeader ? collapsed ? 48 + 32 : 200 + 32 : 0}px)` }"></MultiTab>
      <RouteView v-if="isAlive"></RouteView>
    </a-layout-content>
    <!-- 底部footer -->
    <Footer></Footer>
  </a-layout>
</template>

<script>
import Logo from './Logo'
import { SideMenu } from './Menu'
import SiderTrigger from './SiderTrigger'
import RouteView from './RouteView'
import config from '@/config/mixins/config'
import user from '@/config/mixins/user'
import HeaderRight from './HeaderRight'
import MultiTab from './MultiTab'
import Footer from './Footer'
import refresh from './mixins/refresh'
export default {
  name: 'SiderLayout',
  components: {
    Logo,
    SideMenu,
    SiderTrigger,
    RouteView,
    HeaderRight,
    MultiTab,
    Footer
  },
  mixins: [config, user, refresh],
  data () {
    return {
      // 菜单折叠展开
      collapsed: false
    }
  }
}
</script>

<style lang="less">
@import "./Style/sider";
</style>
