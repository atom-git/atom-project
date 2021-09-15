<template>
  <div class="atom-page-layout">
    <a-row>
      <a-col :sm="24" :md="12" :xl="4">
        <a-card>
          <a-statistic title="在线用户数" :value="onlineUser" :valueStyle="{ fontSize: '32px' }">
            <template #prefix>
              <IconFont type="atom-logo"/>
            </template>
          </a-statistic>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
/**
 * 首页示例
 */
export default {
  name: 'Home',
  data () {
    return {
      // 在线用户数
      onlineUser: 0,
      // 在线用户数订阅
      subscribe: null
    }
  },
  mounted () {
    // 订阅在线用户数
    this.$stomp.subscribe(this.$api.system.STOMP_ONLINE_USER, onlineUser => {
      this.onlineUser = onlineUser
    }).then(subscribe => {
      this.subscribe = subscribe
    })
  },
  unmounted () {
    // 取消订阅在线用户数
    this.$stomp.unsubscribe(this.subscribe)
  }
}
</script>
