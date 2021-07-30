<template>
  <a-layout-header>
    <!-- logo -->
    <Logo :title="title"></Logo>
    <!-- 右侧功能按钮 -->
    <HeaderRight></HeaderRight>
    <!-- 右侧菜单drawer弹出按钮 -->
    <div class="atom-sider-trigger" @click="drawerVisible = !drawerVisible">
      <IconFont :type="drawerVisible ? 'MenuUnfoldOutlined' : 'MenuFoldOutlined'"></IconFont>
    </div>
    <!-- 右侧菜单drawer -->
    <a-drawer placement="right"
              :closable="false"
              :wrapClassName="`atom-mobile-drawer ${colorTheme}`"
              :width="drawerWidth"
              v-model:visible="drawerVisible">
      <SideMenu :menus="menus"
                :theme="drawerTheme"
                :replaceKeys="{ title: 'name', key: 'route' }"
                @on-menu-click="drawerVisible = false"></SideMenu>
      <!-- drawer中的footer -->
      <Footer></Footer>
    </a-drawer>
  </a-layout-header>
  <a-layout-content>
    <RouteView v-if="isAlive"></RouteView>
  </a-layout-content>
  <!-- 底部footer -->
  <Footer></Footer>
</template>

<script>
import Logo from './Logo'
import { SideMenu } from './Menu'
import RouteView from './RouteView'
import config from '@/config/mixins/config'
import user from '@/config/mixins/user'
import HeaderRight from './HeaderRight'
import Footer from './Footer'
import refresh from './mixins/refresh'
export default {
  name: 'DrawerLayout',
  components: {
    Logo,
    SideMenu,
    RouteView,
    HeaderRight,
    Footer
  },
  mixins: [config, user, refresh],
  data () {
    return {
      // drawer菜单折叠展开
      drawerVisible: false
    }
  },
  computed: {
    // drawer模式下的主题
    drawerTheme () {
      return this.theme === 'dark' ? 'dark' : 'light'
    },
    // drawer的宽度，最大不超过320
    drawerWidth () {
      return this.clientWidth > 320 ? 300 : '100%'
    }
  },
}
</script>

<style lang="less">
@import "./Style/drawer";
</style>
