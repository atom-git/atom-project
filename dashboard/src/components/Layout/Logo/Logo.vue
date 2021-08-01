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
.atom-logo {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding: 16px 16px;
  &.collapsed {
    padding: 16px 8px;
  }
  a {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 32px;
    h1, img {
      display: inline-block;
      vertical-align: middle;
    }
    h1 {
      margin: 0 0 0 12px;
      overflow: hidden;
      font-weight: 600;
      animation: fade-in;
      animation-duration: .2s;
      color: @text-color;
    }
  }
  /*不同大小的logo配置*/
  &.small {
    line-height: 24px;
    a {
      h1, img {
        height: 24px;
      }
      h1 {
        font-size: 12px;
        line-height: 24px;
      }
    }
  }
  &.default {
    line-height: 32px;
    a {
      h1, img {
        height: 32px;
      }
      h1 {
        font-size: 18px;
        line-height: 32px;
      }
    }
  }
  &.large {
    line-height: 44px;
    a {
      h1, img {
        height: 44px;
      }
      h1 {
        font-size: 32px;
        line-height: 44px;
      }
    }
  }
}
</style>
