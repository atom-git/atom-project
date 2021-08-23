<template>
  <a-dropdown :trigger="['click']" v-model="dropdown" placement="bottomCenter">
    <span class="atom-header-button">
      <a-badge @click="toogleDropDown"
               count="5"
               :numberStyle="{ backgroundColor: '#ff4d4f', padding: '0 8px', marginTop: '-5px' }">
        <IconFont type="BellOutlined"/>
      </a-badge>
    </span>
    <template #overlay>
      <a-spin :spinning="loading">
        <a-tabs class="atom-notice-container">
          <a-tab-pane v-for="noticeTab in noticeTabs"
                      :tab="`${noticeTab.name}(${noticeMsgs[noticeTab.key].unRead})`"
                      :key="noticeTab.key">
            <a-list class="atom-notice-item-list"
                    :dataSource="noticeMsgs[noticeTab.key].list">
              <template #renderItem="{ item }">
                <a-list-item :key="item.id"
                             :class="item.read ? 'atom-read' : ''"
                             @click="handleNoticeClick(item)">
                  <a-list-item-meta :title="item.title" :description="fromNow(item.msgTime)">
                    <template #avatar>
                      <a-avatar :style="{ backgroundColor: randomColor() }">
                        <IconFont v-if="item.icon" :type="item.icon"/>
                        <template v-else>{{ noticeTab.name }}</template>
                      </a-avatar>
                    </template>
                  </a-list-item-meta>
                </a-list-item>
              </template>
              <template #footer>
                <a-row>
                  <a-col span="12" @click="handleDeleteNotice(noticeTab.key)">清空{{ noticeTab.name }}</a-col>
                  <a-col span="12" @click="handleMoreNotice(noticeTab.key)">查看更多</a-col>
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
      loading: false,
      dropdown: false,
      noticeTabs: [
        { name: '通知', key: 'notice' },
        { name: '消息', key: 'message' },
        { name: '待办', key: 'todo' }
      ],
      noticeMsgs: {
        notice: {
          unRead: 9,
          list: [
            { id: 1, title: '收到面试邀约', icon: 'MailOutlined', msgTime: '1586671701696', route: '/', read: true },
            { id: 2, title: '来自未来的邮件', icon: 'MailOutlined', msgTime: '1586571701696', route: '/', read: false },
            { id: 3, title: '明天检查消防设施', icon: 'WechatOutlined', msgTime: '1585671701696', route: '/', read: true },
            { id: 4, title: '出货单已发', icon: 'TaobaoCircleOutlined', msgTime: '1584671601696', route: '/', read: false },
            { id: 5, title: '努力才会有收获', icon: '', msgTime: '1582371501696', route: '/', read: false }
          ]
        },
        message: {
          unRead: 2,
          list: [
            { id: 1, title: '来自未来的评论', icon: 'QqOutlined', msgTime: '1586671701', route: '/', read: false },
            { id: 2, title: 'Good Job', icon: 'GithubOutlined', msgTime: '1586571701', route: '/', read: false },
            { id: 3, title: 'Atom评论了你', icon: 'WeiboOutlined', msgTime: '1585671701', route: '/', read: true }
          ]
        },
        todo: {
          unRead: 3,
          list: [
            { id: 1, title: '事情尽快完成', icon: 'QqOutlined', msgTime: '2020-03-29', route: '/', read: false },
            { id: 2, title: '努力才会有收获', icon: 'WechatOutlined', msgTime: '2020-03-10', route: '/', read: true },
            { id: 3, title: '上报每周工作进展', icon: 'WechatOutlined', msgTime: '2020-02-29', route: '/', read: false },
            { id: 5, title: 'Atom Project TODO List', icon: 'GithubOutlined', msgTime: '2019-01-29', route: '/', read: false }
          ]
        }
      }
    }
  },
  mounted () {
    this.$stomp.subscribe(this.$api.system.news.STOMP_FETCH_NEWS, (msg) => {
      console.log(msg)
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
    // 获取随机颜色
    randomColor () {
      return this.$utils.randomColor()
    },
    // 响应消息提醒点击
    handleNoticeClick (msg) {
      console.log(msg)
    },
    // 清除消息提醒
    handleDeleteNotice (noticeType) {
      console.log(noticeType)
    },
    // 查看更多提醒
    handleMoreNotice (noticeType) {
      console.log(noticeType)
    }
  }
}
</script>
