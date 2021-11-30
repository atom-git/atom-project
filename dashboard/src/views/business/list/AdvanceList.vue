<template>
  <FuncList :title="title"
            :apiUrl="apiUrl"
            :columns="columns"
            itemLayout="vertical"
            :loadMore="loadMore"
            :funcZone="funcZone"
            :linkable="true"
            :actions="actions"
            @list-func-action="handleFuncAction"
            @list-load-more="handleLoadMore"
            @list-title-link="handleTitleLink"
            @list-row-selection="handleRowSelection"
            @list-row-action="handleRowAction"></FuncList>
</template>

<script>
import { FuncList } from '@/components/Common/FuncTable'
export default {
  name: 'AdvanceList',
  components: { FuncList },
  data () {
    return {
      title: '基础列表测试',
      apiUrl: '/system/news/list',
      loadMore: false,
      columns: [
        { key: 'title', title: '标题', dataIndex: 'title',
          format: { type: 'formatBadge', field: 'status', options: [{ value: 0, color: 'red' }] } },
        { key: 'avatar', title: '头像', dataIndex: 'avatar' },
        { key: 'description', title: '消息时间', dataIndex: 'createTime' }
      ],
      actions: [
        { icon: 'StarOutlined', title: '收藏', name: 'star' },
        { icon: 'LikeOutlined', title: '点赞', name: 'like' }
      ],
      // 用户功能按钮区域
      funcZone: {
        add: true,
        check: true,
        delete: true,
        download: true,
        refresh: true
      }
    }
  },
  methods: {
    // 响应功能区域操作
    handleFuncAction (action, extend) {
      console.log(action, extend)
    },
    // 响应加载更多
    handleLoadMore () {
      console.log('load more')
    },
    // 响应标题跳转
    handleTitleLink (item) {
      console.log(item)
    },
    // 响应行选择
    handleRowSelection (selectedRowKeys, selectedRows) {
      console.log(selectedRowKeys, selectedRows)
    },
    // 响应扩展操作
    handleRowAction (action, row) {
      console.log(action, row)
    }
  }
}
</script>
