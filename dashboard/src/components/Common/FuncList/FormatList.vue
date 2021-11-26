<template>
  <a-list v-bind="$attrs"
          :dataSource="dataSource"
          :pagination="pagination"
          :loading="loading">
    <!-- 是否有加载更多 -->
    <template v-if="loadMore" #loadMore>
      <a-button>{{ loading ? '加载中...' : '加载更多' }}</a-button>
    </template>
    <template #renderItem="{ item }">
      <a-list-item>{{ item }}</a-list-item>
    </template>
  </a-list>
</template>

<script>
export default {
  name: 'FormatList',
  props: {
    // 数据列表
    dataSource: {
      type: Array,
      required: false
    },
    // 分页信息
    pagination: {
      type: [Boolean, Object],
      default: false
    },
    // 是否展示加载更多，与pagination互斥，pagination优先级高
    loadMore: {
      type: Boolean,
      default: false
    },
    // 是否加载中
    loading: {
      type: Boolean,
      default: false
    }
  }
}
</script>
