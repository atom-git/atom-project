<template>
  <a-dropdown :trigger="['click']" v-model="dropdown" placement="bottomCenter">
    <span class="atom-header-button">
      <a-badge @click="toogleDropDown"
               :count="unreadCnt"
               :numberStyle="{ backgroundColor: '#ff4d4f', padding: '0 8px', marginTop: '-5px' }">
        <IconFont type="BellOutlined"/>
      </a-badge>
    </span>
    <template #overlay>
      <a-spin :spinning="loading">
        <a-tabs class="atom-news-container">
          <a-tab-pane v-for="newsTab in newsTabs"
                      :tab="`${newsTab.name}(${newsMap[newsTab.key].unRead})`"
                      :key="newsTab.key">
            <a-list :dataSource="newsMap[newsTab.key].list">
              <template #renderItem="{ item, index }">
                <a-list-item :key="item.id"
                             :class="item.status ? 'atom-read' : ''"
                             @click="handleNewsClick(item)">
                  <a-list-item-meta :title="item.title" :description="fromNow(item.createTime)">
                    <template #avatar>
                      <a-avatar :style="{ backgroundColor: colorSet[index] }"
                                :src="(item.fromUser && item.fromUser.head) || undefined">
                        {{ (item.title || newsTab).charAt(0) }}
                      </a-avatar>
                    </template>
                  </a-list-item-meta>
                </a-list-item>
              </template>
              <template #footer>
                <a-row>
                  <a-col span="12" @click="handleClearNews(newsTab.key)">清空{{ newsTab.name }}</a-col>
                  <a-col span="12" @click="handleMoreNews(newsTab.key)">查看更多</a-col>
                </a-row>
              </template>
            </a-list>
          </a-tab-pane>
        </a-tabs>
      </a-spin>
    </template>
  </a-dropdown>
</template>

<script>
export default {
  name: 'Notice',
  data () {
    return {
      // 是否加载中
      loading: false,
      // 下拉是否显示
      dropdown: false,
      // 系统消息tabs
      newsTabs: [
        { name: '通知', key: 'notice' },
        { name: '消息', key: 'message' },
        { name: '待办', key: 'todo' }
      ],
      // 未读信息数量
      unreadCnt: 0,
      // 消息列表
      newsMap: {},
      // 获取随机颜色
      colorSet: this.$default.colorSet
    }
  },
  mounted () {
    this.$stomp.subscribe(this.$api.system.news.STOMP_FETCH_NEWS, (userNews) => {
      this.unreadCnt = userNews['noticeUnreadCnt'] + userNews['messageUnreadCnt'] + userNews['todoUnreadCnt']
      this.newsMap.notice = {
        unRead: userNews['noticeUnreadCnt'],
        list: userNews['noticeList']
      }
      this.newsMap.message = {
        unRead: userNews['messageUnreadCnt'],
        list: userNews['messageList']
      }
      this.newsMap.todo = {
        unRead: userNews['todoUnreadCnt'],
        list: userNews['todoList']
      }
    })
  },
  methods: {
    // 切换下拉菜单
    toogleDropDown () {
      this.dropDwon = !this.dropDwon
    },
    // 格式化到日前的时间
    fromNow (time) {
      return this.$utils.fromNow(time)
    },
    // 响应消息提醒点击
    handleNewsClick (news) {
      if (news.status === 0) {
        // 设置当前为已读
        this.$api.system.news.read(news.id).then(() => {
          news.status = 1
        })
      }
    },
    // 清除消息未读
    handleClearNews (newsType) {
      const newsIds = this.newsMap[newsType].list.filter(item => item.status === 0).map(item => item.id)
      // 设置当前内容项的消息为已读
      if (this.$utils.isValid(newsIds)) {
        this.$api.system.news.read(newsIds).then(() => {
          this.newsMap[newsType].list.filter(item => item.status === 0).forEach(item => item.status = 1)
        })
      }
    },
    // 查看更多提醒
    handleMoreNews () {
      this.dropdown = false
      this.$router.push({ name: 'sysnews' })
    }
  }
}
</script>
