<template>
  <!-- 手机端的呈现方式 -->
  <template v-if="layout === $default.mobileLayout">
    <a-collapse :bordered="false" expandIconPosition="right">
      <template #expandIcon="{ isActive }">
        <IconFont type="CaretLeftOutlined" :rotate="isActive ? -90 : 0" />
      </template>
      <a-collapse-panel key="sider">
        <template #header>
          <IconFont type="PartitionOutlined" />  {{ sideTitle }}
        </template>
        <!-- 侧边部分 -->
        <slot name="sider"></slot>
      </a-collapse-panel>
    </a-collapse>
    <!-- 内容部分 -->
    <slot name="content"></slot>
  </template>
  <!-- PC端的呈现方式 -->
  <a-row :gutter="16" v-else>
    <a-col :xs="leftSpan.xs" :sm="leftSpan.sm" :md="leftSpan.md" :lg="leftSpan.lg" :xl="leftSpan.xl" :xxl="leftSpan.xxl">
      <!-- 侧边部分 -->
      <slot name="sider"></slot>
    </a-col>
    <a-col :xs="rightSpan.xs" :sm="rightSpan.sm" :md="rightSpan.md" :lg="rightSpan.lg" :xl="rightSpan.xl" :xxl="rightSpan.xxl">
      <!-- 内容部分 -->
      <slot name="content"></slot>
    </a-col>
  </a-row>
</template>

<script>
/**
 * 页面左右布局，正常模式下左右分隔，在device is mobile模式下，显示为右侧的drawer
 */
const defaultSide = { xs: 10, sm: 8, md: 8, lg: 7, xl: 6, xxl: 5 }
import config from '@/config/mixins/config'
export default {
  name: 'SideLayout',
  mixins: [config],
  props: {
    // 左侧边的布局
    side: {
      type: [Number, Object],
      default: () => { return defaultSide }
    },
    // 左侧sider的title，仅当为drawer展示模式时呈现，用于展开左侧筛选项
    sideTitle: {
      type: String,
      required: false
    }
  },
  computed: {
    // 左侧span
    leftSpan () {
      if (Number.isInteger(this.side)) {
        return { xs: this.side, sm: this.side, md: this.side, lg: this.side, xl: this.side, xxl: this.side }
      } else {
        return Object.assign({}, defaultSide, this.side)
      }
    },
    // 右侧span
    rightSpan () {
      return { xs: 24 - this.leftSpan.xs, sm: 24 - this.leftSpan.sm, md: 24 - this.leftSpan.md, lg: 24 - this.leftSpan.lg, xl: 24 - this.leftSpan.xl, xxl: 24 - this.leftSpan.xxl }
    }
  }
}
</script>

<style lang="less">
.ant-collapse.ant-collapse-borderless {
  .ant-collapse-item {
    border: none;
  }
}
</style>
