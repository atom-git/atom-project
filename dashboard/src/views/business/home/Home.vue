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
export default {
  name: 'Home',
  data () {
    return {
      // 在线用户数
      onlineUser: 0
    }
  },
  mounted () {
    this.$stomp.subscribe('/stomp/topic/onlineUser', onlineUser => {
      this.onlineUser = onlineUser
    })
  }
}
</script>
