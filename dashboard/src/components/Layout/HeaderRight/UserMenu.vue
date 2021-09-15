<template>
  <a-dropdown :trigger="['hover', 'click']">
    <span class="atom-header-button">
      <a-avatar size="small" :alt="iconName">
        <template #icon>
          <IconFont type="UserOutlined"/>
        </template>
      </a-avatar>
      <span>{{ userInfo.name }}</span>
    </span>
    <template #overlay>
      <a-menu @click="handleClick">
        <a-menu-item key="userCenter">
          <IconFont type="GithubOutlined"/><span>{{ $t('Layout.UserMenu.center') }}</span>
        </a-menu-item>
        <a-menu-item key="userSetUp">
          <IconFont type="WindowsOutlined"/><span>{{ $t('Layout.UserMenu.setup') }}</span>
        </a-menu-item>
        <a-menu-divider />
        <a-menu-item key="signOut">
          <IconFont type="LogoutOutlined"/><span>{{ $t('Layout.UserMenu.logout') }}</span>
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
</template>

<script>
import user from '@/config/mixins/user'
const chinese = /^[\u4E00-\u9FA5]{1,5}$/
const english = /^[A-Za-z]+$/
export default {
  name: 'UserMenu',
  mixins: [user],
  computed: {
    iconName () {
      let userName = this.userInfo.name || ''
      // 如果是中文结尾，则取最后一个字
      if (chinese.test(userName)) {
        return userName.substr(userName.length - 1, 1)
      }
      // 如果是纯英文，则取第一个单词,最长取4位的，否则取首字母
      if (english.test(userName)) {
        userName = userName.split(' ')[0]
        return userName.length > 5 ? userName.substr(0, 1) : userName
      }
      // 混杂的直接取第一个字符
      return userName.substr(0, 1)
    }
  },
  methods: {
    // 响应菜单点击
    handleClick (menu) {
      // 点击非当前路由时跳转
      if (menu.key === 'userCenter' || menu.key === 'userSetUp') {
        if (menu.key !== this.curMenu) {
          this.$router.push({ name: menu.key })
        }
      } else if (menu.key === 'signOut') {
        // 清除消息服务
        this.$stomp.disconnect()
        // 登出
        this.$store.dispatch('signOut').then(() => {
          // 清空权限
          this.$store.dispatch('clearPermission')
          // 跳转至登陆页面
          this.$router.replace({ name: 'signIn' })
        })
      }
    }
  }
}
</script>
