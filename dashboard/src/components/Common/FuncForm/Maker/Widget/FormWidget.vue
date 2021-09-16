<template>
  <a-collapse-panel :header="`${title}[${widgets.length}]`">
    <a-list :grid="{ gutter: 8, column: 2 }" :dataSource="widgets">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-card :title="item.title">Card content</a-card>
        </a-list-item>
      </template>
    </a-list>
  </a-collapse-panel>
</template>

<script>
/**
 * 表单组件
 */
export default {
  name: 'FormWidget',
  props: {
    // 面板标题
    title: {
      type: String,
      default: ''
    },
    // 组件列表
    widgets: {
      type: Array,
      default: () => ([])
    }
  }
}
</script>
