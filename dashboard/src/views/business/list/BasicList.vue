<template>
  <FormatList :dataSource="dataSource"
              :pagination="pagination"
              :loading="loading"></FormatList>
</template>

<script>
import { FormatList } from '@/components/Common/FuncList'
const dataSource = []
for (let i = 0; i < 23; i++) {
  dataSource.push({
    href: 'https://www.antdv.com/',
    title: `ant design vue part ${i}`,
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
    description:
        'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    content:
        'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
  })
}
export default {
  name: 'BasicList',
  components: { FormatList },
  data () {
    return {
      dataSource: [],
      pagination: {},
      loadMore: false,
      loading: false
    }
  }
}
</script>
