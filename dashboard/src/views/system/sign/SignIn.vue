<template>
  <a-form class="atom-sign-form" ref="signForm" :model="signUser" :rules="signRules">
    <a-tabs v-model:activeKey="signUser.type">
      <a-tab-pane key="account" tab="帐户密码登录">
        <a-form-item name="account">
          <a-input v-model:value="signUser.account" placeholder="请输入帐号" size="large" allowClear>
            <template #prefix>
              <IconFont type="UserOutlined"/>
            </template>
          </a-input>
        </a-form-item>
        <a-form-item name="password">
          <a-input-password v-model:value="signUser.password" placeholder="请输入密码" size="large" allowClear>
            <template #prefix>
              <IconFont type="LockOutlined"/>
            </template>
          </a-input-password>
        </a-form-item>
      </a-tab-pane>
      <a-tab-pane key="phone" tab="手机号登录">
        <a-form-item name="phone">
          <a-input v-model:value="signUser.phone" placeholder="请输入手机号" size="large" allowClear>
            <template #prefix>
              <IconFont type="MobileOutlined"/>
            </template>
          </a-input>
        </a-form-item>
        <a-form-item name="verifyCode" class="atom-form-flex">
          <a-input v-model:value="signUser.verifyCode" placeholder="请输入验证码" size="large" allowClear>
            <template #prefix>
              <IconFont type="MessageOutlined"/>
            </template>
          </a-input>
          <a-button class="atom-form-flex-right"
                    type="primary"
                    ghost
                    @click="sendVerifyCode"
                    size="large"
                    :disabled="timeOut !== null">
            <span v-if="!timeOut">发送验证码</span>
            <a-statistic-countdown v-else
                                   :value="timeOut"
                                   prefix="已发送"
                                   suffix="秒"
                                   format="ss"
                                   @finish="onFinished"></a-statistic-countdown>
          </a-button>
        </a-form-item>
      </a-tab-pane>
    </a-tabs>
    <a-form-item class="atom-form-flex">
      <a-checkbox>自动登录</a-checkbox>
      <a-button type="link" @click="handleForget">忘记密码</a-button>
    </a-form-item>
    <a-form-item>
      <a-button type="primary" :loading="loading" size="large" block @click="handleSignIn">登录</a-button>
    </a-form-item>
    <a-form-item class="atom-form-flex">
      其他登录：
      <a-avatar-group>
        <a-avatar><template #icon><IconFont type="QqOutlined"/></template></a-avatar>
        <a-avatar><template #icon><IconFont type="WechatOutlined"/></template></a-avatar>
        <a-avatar><template #icon><IconFont type="AlipayCircleOutlined"/></template></a-avatar>
      </a-avatar-group>
      <a-button type="link" @click="handleSignUp">注册帐号</a-button>
    </a-form-item>
  </a-form>
</template>

<script>
export default {
  name: 'SignIn',
  data () {
    return {
      signUser: {
        // 登录方式
        type: 'account',
        // 帐号信息
        account: '',
        // 手机号码
        phone: '',
        // 密码
        password: '',
        // 验证码
        verifyCode: ''
      },
      // 登录超时时间
      timeOut: null,
      // 是否正在加载
      loading: false
    }
  },
  computed: {
    // 登录校验规则
    signRules () {
      if (this.signUser.type === 'account') {
        return {
          account: [{ trigger: 'blur', required: true, message: '请输入帐号' }],
          password: [{ trigger: 'blur', required: true, message: '请输入密码' }]
        }
      } else {
        return {
          phone: [{ trigger: 'blur', required: true, message: '请输入手机号' }],
          verifyCode: [{ trigger: 'blur', required: true, message: '请输入验证码' }]
        }
      }
    }
  },
  methods: {
    // 获取验证码
    sendVerifyCode () {
      this.$refs.signForm.validate(['phone']).then(() => {
        // 调用后台短信网关
        this.$api.system.sendVerifyCode(this.signUser.phone).then(() => {
          this.$message.success('验证码发送成功')
        })
        this.timeOut = Date.now() + 1000 * 60
      })
    },
    // 倒计时结束
    onFinished () {
      this.timeOut = null
    },
    // 响应登录
    handleSignIn () {
      this.loading = true
      // 校验输入
      this.$refs.signForm.validate().then(() => {
        // 登录
        this.$store.dispatch('signIn', this.signUser).then(() => {
          // 拉取用户信息
          this.$store.dispatch('getUser').then(({ menus, actions, appConfig }) => {
            // 设置App应用配置信息
            this.$store.dispatch('setConfig', appConfig).then(() => {
              // 生成权限信息
              this.$store.dispatch('generatePermission', { menus, actions }).then(() => {
                this.$router.replace({ name: this.$default.HOME_PAGE })
              }).catch(() => this.loading = false)
            }).catch(() => this.loading = false)
          }).catch(() => this.loading = false)
        }).catch(() => this.loading = false)
      }).catch(() => this.loading = false)
    },
    // 响应忘记密码
    handleForget () {
      this.$router.push({ name: 'forget' })
    },
    // 响应注册
    handleSignUp () {
      this.$router.push({ name: 'signUp' })
    }
  }
}
</script>
