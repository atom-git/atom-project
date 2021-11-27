<template>
  <FormatList :title="title"
              :dataSource="dataSource"
              itemLayout="vertical"
              :pagination="pagination"
              :loadMore="loadMore"
              :fieldKeys="fieldKeys"
              :linkable="true"
              :actions="actions"
              :loading="loading"
              :itemTitleFormat="itemTitleFormat"
              @list-item-link="handleLink"
              @list-item-action="handleAction"></FormatList>
</template>

<script>
import { FormatList } from '@/components/Common/FuncList'
const dataSource = []
for (let i = 0; i < 23; i++) {
  dataSource.push({
    title: `Ant Design Vue part ${i}`,
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
    description:
        'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    content:
        'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
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
      loading: false
    }
  },
  methods: {
    // 响应标题跳转
    handleLink (item) {
      console.log(item)
    },
    // 响应扩展操作
    handleAction (action) {
      console.log(action)
    }
  }
}
</script>
