<template>
  <div :class="['atom-logo', collapsed ? 'collapsed' : '', size ]">
    <router-link to="/home">
      <img src="@/assets/logo.png" alt="logo" />
      <h1>{{ title }}</h1>
    </router-link>
    <div v-if="desc" class="atom-logo-desc">{{ desc }}</div>
  </div>
</template>

<script>
/**
 * 菜单logo
 */
export default {
  name: 'Logo',
  props: {
    // 应用标题
    title: {
      type: String,
      required: true
    },
    // 是否折叠
    collapsed: {
      type: Boolean,
      default: false
    },
    // logo size[small default large]
    size: {
      type: String,
      default: 'default'
    },
    // 系统描述
    desc: {
      type: String,
      required: false
    }
  }
}
</script>

<style lang="less">
@import "logo";
</style>
