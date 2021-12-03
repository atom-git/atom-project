<template>
  <FormatList itemLayout="vertical"
              :title="title"
              :dataSource="dataSource"
              :loadMore="loadMore"
              :columns="columns"
              :funcZone="funcZone"
              :loading="loading"
              @list-func-action="handleFuncAction"
              @list-load-more="handleLoadMore"
              @list-row-selection="handleRowSelection"
              @list-row-action="handleRowAction"></FormatList>
  <FormList :title="title"
            :columns="columns"
            :dataSource="dataSource"
            itemLayout="vertical"
            :loadMore="loadMore"
            :funcZone="funcZone"
            :loading="loading"
            @list-filter="handleFilter"
            @list-func-action="handleFuncAction"
            @list-load-more="handleLoadMore"
            @list-row-selection="handleRowSelection"
            @list-row-action="handleRowAction"
            @list-form-submit="handleFormSubmit"
            @list-form-cancel="handleFormCancel"></FormList>
</template>

<script>
import { FormatList, FormList } from '@/components/Advance/FuncList'
const dataSource = []
for (let i = 0; i < 13; i++) {
  dataSource.push({
    title: `Atom Project项目组件标题${i}`,
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
    description:
        '这是一个FuncList组件的测试组件，这个是描述字段表述description字段，长描述字段。',
    content:
        '希望像一个家庭一样，没有它，你会觉得生活乏味；有了它，你又觉得天天为它辛劳，是一种烦恼。——马克·吐温；一个希望的突然失落会留下一处伤痕，即使那希望最终实现，也决不能使它完全平复。——托马斯·哈代',
    extra: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
    state: i % 2
  })
}
export default {
  name: 'BasicList',
  components: { FormatList, FormList },
  data () {
    return {
      title: '基础列表测试',
      dataSource: dataSource,
      pagination: true,
      loadMore: true,
      columns: [
        { key: 'title', title: '标题', dataIndex: 'title',
          optionField: 'state', options: [{ value: 0, count: 'new' }],
          format: 'formatBadge' },
        { key: 'avatar', title: '头像', dataIndex: 'avatar' },
        { key: 'description', title: '描述', dataIndex: 'description' },
        { key: 'content', title: '内容', dataIndex: 'content', form: { maxlength: 200 } },
        { key: 'extra', title: '图例', dataIndex: 'extra', width: 120 },
        { key: 'actions', actions: [
            { icon: 'StarOutlined', title: '收藏', name: 'star' },
            { icon: 'LikeOutlined', title: '点赞', name: 'like' },
            { icon: 'MessageOutlined', title: '评论' }
          ] },
        { title: '时间', dataIndex: 'dataTime', form: { type: 'dataPicker' } }
      ],
      // 用户功能按钮区域
      funcZone: {
        add: true,
        check: true,
        delete: true,
        download: true,
        refresh: true
      },
      loading: false
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
    }
  }
}
</script>
