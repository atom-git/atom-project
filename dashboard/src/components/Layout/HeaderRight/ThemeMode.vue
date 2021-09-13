<template>
  <a-dropdown :trigger="['click']">
    <span class="atom-header-button"><IconFont type="BgColorsOutlined"/></span>
    <template #overlay>
      <a-menu @click="handleThemeToggle">
        <a-menu-item key="light">
          <IconFont type="atom-theme-light"/>纯白世界
        </a-menu-item>
        <a-menu-item key="dark">
          <IconFont type="atom-theme-dark"/>暗黑世界
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
</template>

<script>
import { toggleTheme } from '@/config/theme'
/**
 * 主题切换黑白
 */
export default {
  name: 'ThemeMode',
  data () {
    return {
      theme: this.$store.getters.appConfig.theme,
      primaryColor: this.$store.getters.appConfig.primaryColor
    }
  },
  methods: {
    // 响应主题切换
    handleThemeToggle ($event) {
      const theme = $event.key || this.theme
      const loadding = this.$message.loading('正在切换主题！', 1)
      toggleTheme(theme, this.primaryColor).then(() => {
        loadding.then(
            () => { this.$message.success('主题切换成功！', 1) },
            () => { this.$message.error('主题切换失败！', 2) })
        // 保存主题到缓存
        this.$store.dispatch('setTheme', theme)
        this.$store.dispatch('setPrimaryColor', this.primaryColor)
      })
    },
  }
}
</script>
