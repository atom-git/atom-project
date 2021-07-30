<template>
  <a-dropdown :trigger="['hover', 'click']">
    <span class="atom-header-button"><IconFont type="GlobalOutlined"/></span>
    <template #overlay>
      <a-menu @click="handleClick">
        <a-menu-item key="cn">
          🇨🇳<span>简体中文</span>
        </a-menu-item>
        <a-menu-item key="us">
          🇺🇸<span>English</span>
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
</template>

<script>
export default {
  name: 'Global',
  methods: {
    handleClick (language) {
      console.log(language)
    }
  }
}
</script>
