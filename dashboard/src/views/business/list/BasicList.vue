<template>
  <FormatList :title="title"
              :dataSource="dataSource"
              itemLayout="vertical"
              :pagination="pagination"
              :loadMore="loadMore"
              :fieldKeys="fieldKeys"
              :funcZone="funcZone"
              :linkable="true"
              :actions="actions"
              :loading="loading"
              :itemTitleFormat="itemTitleFormat"
              @list-func-action="handleFuncAction"
              @list-load-more="handleLoadMore"
              @list-title-link="handleTitleLink"
              @list-row-selection="handleRowSelection"
              @list-row-action="handleRowAction"></FormatList>
</template>

<script>
import { FormatList } from '@/components/Common/FuncList'
const dataSource = []
for (let i = 0; i < 23; i++) {
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
  components: { FormatList },
  data () {
    return {
      title: '基础列表测试',
      dataSource: dataSource,
      pagination: false,
      loadMore: true,
      fieldKeys: { avatar: 'avatar', title: 'title', description: 'description', content: 'content', extra: 'extra' },
      actions: [
        { icon: 'StarOutlined', title: '收藏', name: 'star' },
        { icon: 'LikeOutlined', title: '点赞', name: 'like' },
        { icon: 'MessageOutlined', title: '评论' }
      ],
      itemTitleFormat: {
        type: 'formatBadge',
        field: 'state',
        options: [{ value: 0, color: 'purple' }]
      },
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
