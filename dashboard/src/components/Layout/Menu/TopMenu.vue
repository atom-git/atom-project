<template>
  <div class="atom-top-menu">
    <a-menu v-if="!refresh"
            mode="horizontal"
            v-model:openKeys="openKeys"
            v-model:selectedKeys="selectedKeys"
            :theme="theme"
            @click="handleClick">
      <template v-for="menu in menus">
        <a-menu-item v-if="!$utils.isValid(menu[replaceFields.children])"
                     :key="menu[replaceFields.key]">
          <template #icon>
            <IconFont :type="menu[replaceFields.icon]" />
          </template>
          {{ menu[replaceFields.title] }}
        </a-menu-item>
        <SubMenu v-else
                 :key="menu[replaceFields.key]"
                 :subMenu="menu"
                 :replaceFields="replaceFields"></SubMenu>
      </template>
    </a-menu>
  </div>
</template>

<script>
import action from './mixins/action'
export default {
  name: 'TopMenu',
  mixins: [action]
}
</script>
