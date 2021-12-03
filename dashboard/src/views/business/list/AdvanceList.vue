<template>
  <FuncList :title="title"
            :apiUrl="apiUrl"
            :columns="columns"
            itemLayout="horizontal"
            :loadMore="loadMore"
            :funcZone="funcZone"
            @list-filter="handleFilter"
            @list-func-action="handleFuncAction"
            @list-load-more="handleLoadMore"
            @list-row-selection="handleRowSelection"
            @list-row-action="handleRowAction"
            @list-form-submit="handleFormSubmit"
            @list-form-cancel="handleFormCancel"></FuncList>
</template>

<script>
import { FuncList } from '@/components/Advance/FuncList'
export default {
  name: 'AdvanceList',
  components: { FuncList },
  data () {
    return {
      title: '基础列表测试',
      apiUrl: '/system/news/list',
      loadMore: true,
      columns: [
        { key: 'title', title: '标题', dataIndex: 'title',
          optionField: 'status', options: [{ value: 0, count: 'new' }],
          format: 'formatBadge' },
        { key: 'avatar', title: '头像', dataIndex: 'avatar' },
        { key: 'content', title: '消息时间', dataIndex: 'createTime', format: 'formatDate' },
        { key: 'actions', format: 'formatActions', actions: [
            { icon: 'StarOutlined', title: '收藏', name: 'star' },
            { icon: 'LikeOutlined', title: '点赞', name: 'like' },
            { icon: 'MessageOutlined', title: '评论' }
          ] }
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
    // 响应过滤查询
    handleFilter (filterModel) {
      console.log(filterModel)
    },
    // 响应功能区域操作
    handleFuncAction (action, extend) {
      console.log(action, extend)
    },
    // 响应加载更多
    handleLoadMore () {
      console.log('load more')
    },
    // 响应行选择
    handleRowSelection (selectedRowKeys, selectedRows) {
      console.log(selectedRowKeys, selectedRows)
    },
    // 响应扩展操作
    handleRowAction (action, row) {
      console.log(action, row)
    },
    // 响应表单提交
    handleFormSubmit (action, model) {
      console.log(action, model)
    },
    // 响应表单取消
    handleFormCancel (action) {
      console.log(action)
    },
  }
}
</script>
