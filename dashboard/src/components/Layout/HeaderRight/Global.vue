<template>
  <a-dropdown :trigger="['click']">
    <span class="atom-header-button"><IconFont type="GlobalOutlined"/></span>
    <template #overlay>
      <a-menu @click="handleClick" :selectedKeys="[appConfig.locale]">
        <a-menu-item key="zh-CN">
          🇨🇳 <span>简体中文</span>
        </a-menu-item>
        <a-menu-item key="en-US">
          🇺🇸 <span>English</span>
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
</template>

<script>
export default {
  name: 'Global',
  data () {
    return {
      // App应用配置
      appConfig: this.$store.getters.appConfig
    }
  },
  methods: {
    handleClick ({ key }) {
      this.$store.dispatch('setLocale', key).then(() => {
        // 更新用户个性化配置
        this.$api.system.user.updateAppConfig(this.appConfig).then(() => {
          this.$store.dispatch('setAppConfig', this.appConfig)
          this.$nextTick(() => {
            this.$message.success('语言设置成功！', 1)
          })
        })
      })
    }
  }
}
</script>
