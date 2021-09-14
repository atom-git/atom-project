<template>
  <div class="atom-sider-trigger">
    <a-menu mode="inline"
            :selectable="false"
            :theme="theme"
            @click="toggleCollapsed">
      <a-menu-item>
        <IconFont :type="collapsed ? 'MenuUnfoldOutlined' : 'MenuFoldOutlined'"></IconFont>
        <span v-if="collapsed">{{ $t('global.unfold') }}</span>
      </a-menu-item>
    </a-menu>
  </div>
</template>

<script>
export default {
  name: 'SiderTrigger',
  props: {
    // 主题颜色
    theme: {
      type: String,
      default: 'light'
    },
    // 折叠展开
    collapsed: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    // 响应菜单折叠展开
    toggleCollapsed () {
      this.$emit('update:collapsed', !this.collapsed)
    }
  }
}
</script>
