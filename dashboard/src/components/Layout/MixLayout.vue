<template>
  <a-layout-header>
    <!-- logo -->
    <Logo :title="title"></Logo>
    <div style="flex: 1 1 0;"></div>
    <!-- 右侧功能按钮 -->
    <HeaderRight></HeaderRight>
  </a-layout-header>
  <a-layout>
    <a-layout-sider class="atom-sider"
                    breakpoint="xl"
                    :theme="mixTheme"
                    :collapsedWidth="48"
                    v-model:collapsed="collapsed">
      <!-- 菜单 -->
      <SideMenu :menus="menus"
                :theme="mixTheme"
                :replaceKeys="{ title: 'name', key: 'route' }"></SideMenu>
      <!-- 折叠弹出按钮 -->
      <SiderTrigger :theme="mixTheme" v-model:collapsed="collapsed"></SiderTrigger>
    </a-layout-sider>
    <!-- 右侧内容部分 -->
    <a-layout :style="{ marginLeft: `${appConfig.fixHeader ? collapsed ? 48 : 200 : 0}px`}">
      <a-layout-content :class="appConfig.multiTab ? 'atom-has-multitab' : ''">
        <!-- 多标签栏 -->
        <MultiTab v-if="appConfig.multiTab" :style="{ width: `calc(100% - ${appConfig.fixHeader ? collapsed ? 48 + 32 : 200 + 32 : 0}px)` }"></MultiTab>
        <RouteView v-if="isAlive"></RouteView>
      </a-layout-content>
      <!-- 底部footer -->
      <Footer></Footer>
    </a-layout>
  </a-layout>
</template>

<script>
import Logo from './Logo'
import { SideMenu } from './Menu'
import SiderTrigger from './SiderTrigger'
import RouteView from './RouteView'
import config from '@/config/mixins/config'
import user from '@/config/mixins/user'
import refresh from '@/config/mixins/refresh'
import HeaderRight from './HeaderRight'
import MultiTab from './MultiTab'
import Footer from './Footer'
export default {
  name: 'MixLayout',
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
  },
  computed: {
    // mix模式下的主题
    mixTheme () {
      return this.appConfig.theme === 'dark' ? 'dark' : 'light'
    }
  },
  methods: {
    // 响应菜单折叠展开
    toggleCollapsed () {
      this.collapsed = !this.collapsed
    }
  }
}
</script>

<style lang="less">
@import "./Style/mix";
</style>
