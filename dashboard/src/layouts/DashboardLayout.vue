<template>
  <a-layout :class="[layoutClass, theme, fixHeader ? 'fixed' : '']">
    <!-- 支持左右布局(sider)，上下布局(top)，顶部侧边布局(mix[top-sider])，移动端抽屉布局(drawer) -->
    <!-- 桌面端-sider -->
    <SiderLayout v-if="layout === 'sider'"></SiderLayout>

    <!-- 桌面端-top -->
    <TopLayout v-else-if="layout === 'top'"></TopLayout>

    <!-- 桌面端-mix -->
    <MixLayout v-else-if="layout === 'mix'"></MixLayout>

    <!-- 移动端 -->
    <DrawerLayout v-else-if="layout === 'drawer'"></DrawerLayout>
  </a-layout>
</template>

<script>
/**
 * 工作台相关布局
 */
import { SiderLayout, TopLayout, MixLayout, DrawerLayout } from '@/components/Layout'
import config from '@/config/mixins/config'
export default {
  name: 'DashboardLayout',
  components: {
    SiderLayout,
    TopLayout,
    MixLayout,
    DrawerLayout
  },
  mixins: [config],
  computed: {
    layoutClass () {
      return `atom-${this.layout}-layout`
    }
  }
}
</script>

<style lang="less">
@import "Style/dashboard";
</style>
