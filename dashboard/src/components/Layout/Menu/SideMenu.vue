<template>
  <div class="atom-sider-menu">
    <a-menu mode="inline"
            v-model:openKeys="openKeys"
            v-model:selectedKeys="selectedKeys"
            :theme="theme"
            @click="handleClick">
      <template v-for="(menu, index) in menus">
        <a-menu-item v-if="!$utils.isValid(menu[replaceFields.children])" :key="menu[replaceFields.key]">
          <IconFont :type="menu[replaceFields.icon]" />
          <span>{{ menu[replaceFields.title] }}</span>
        </a-menu-item>
        <SubMenu v-else
                 :key="index"
                 :subMenu="menu"
                 :replaceFields="replaceFields"></SubMenu>
      </template>
    </a-menu>
  </div>
</template>

<script>
/**
 * 侧边菜单
 */
import action from './mixins/action'
export default {
  name: 'SideMenu',
  mixins: [action],
  mounted () {
    // 侧边菜单在挂载后打开第一个有子菜单的菜单
    const firstOpen = this.menus.find(menu => menu[this.replaceFields.children] && menu[this.replaceFields.children].length > 0)
    this.openKeys = [firstOpen[this.replaceFields.key]]
  }
}
</script>
