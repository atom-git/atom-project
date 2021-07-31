<template>
  <a-form class="atom-sign-form" ref="signForm" :model="signUser" :rules="signRules">
    <a-form-item name="account">
      <a-input v-model:value="signUser.account" placeholder="请输入帐号" size="large" allowClear>
        <template #prefix>
          <IconFont type="UserOutlined"/>
        </template>
      </a-input>
    </a-form-item>
    <a-form-item v-if="device.isMobile" name="password">
      <a-input-password v-model:value="signUser.password" placeholder="请输入密码" size="large" @change="handlePasswordChange" allowClear>
        <template #prefix>
          <IconFont type="LockOutlined"/>
        </template>
      </a-input-password>
      <template #help>
        <div :class="['atom-password-level', passwordLevelEnum[passwordLevel].class]">
          强度：<span class="atom-password-level-desc">{{ passwordLevelEnum[passwordLevel].title }}</span>
        </div>
        <a-progress :percent="passwordLevelEnum[passwordLevel].percent"
                    :showInfo="false"
                    size="small"
                    :strokeColor="passwordLevelEnum[passwordLevel].color"></a-progress>
        <div>密码至少8位【包括大小写字母、数字】</div>
      </template>
    </a-form-item>
    <a-form-item v-else name="password">
      <a-popover placement="left">
        <a-input-password v-model:value="signUser.password" placeholder="请输入密码" size="large" @change="handlePasswordChange" allowClear>
          <template #prefix>
            <IconFont type="LockOutlined"/>
          </template>
        </a-input-password>
        <template #content>
          <div :class="['atom-password-level', passwordLevelEnum[passwordLevel].class]">
            强度：<span class="atom-password-level-desc">{{ passwordLevelEnum[passwordLevel].title }}</span>
          </div>
          <a-progress :percent="passwordLevelEnum[passwordLevel].percent"
                      :showInfo="false"
                      size="small"
                      :strokeColor="passwordLevelEnum[passwordLevel].color"></a-progress>
          <div>密码至少8位【包括大小写字母、数字】</div>
        </template>
      </a-popover>
    </a-form-item>
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
    <a-form-item>
      <a-button type="primary" :loading="loading" size="large" block @click="handleSignUp">注册</a-button>
    </a-form-item>
    <a-form-item class="atom-form-flex">
      其他登录：
      <a-avatar-group>
        <a-avatar><template #icon><IconFont type="QqOutlined"/></template></a-avatar>
        <a-avatar><template #icon><IconFont type="WechatOutlined"/></template></a-avatar>
        <a-avatar><template #icon><IconFont type="AlipayCircleOutlined"/></template></a-avatar>
      </a-avatar-group>
      <a-button type="link" @click="handleSignIn">已有帐号登录</a-button>
    </a-form-item>
  </a-form>
</template>

<script>
import FieldRules from '@/components/Common/FuncForm/Form/mixins/FieldRules'
import config from '@/config/mixins/config'
export default {
  name: 'SignUp',
  mixins: [config],
  data () {
    return {
      signUser: {
        // 帐号信息
        account: '',
        // 手机号码
        phone: '',
        // 密码
        password: '',
        // 验证码
        verifyCode: ''
      },
      // 注册校验规则
      signRules: {
        account: [{ trigger: 'blur', required: true, message: '请输入帐号' }],
        password: [{ trigger: 'blur', required: true, pattern: FieldRules.passwordPattern, message: '请输入密码' }],
        phone: [{ trigger: 'blur', required: true, pattern: FieldRules.mobilePattern, message: '请输入手机号' }],
        verifyCode: [{ trigger: 'blur', required: true, message: '请输入验证码' }]
      },
      passwordLevelEnum: {
        1: { title: '低', class: 'low', percent: 33, color: '#f5222d' },
        2: { title: '中', class: 'middle', percent: 66, color: '#faad14' },
        3: { title: '高', class: 'strong', percent: 100, color: '#52c41a' }
      },
      passwordLevel: 1,
      // 登录超时时间
      timeOut: null,
      // 是否正在加载
      loading: false
    }
  },
  methods: {
    // 获取验证码
    sendVerifyCode () {
      // 调用后台短信网关
      this.$api.system.sendVerifyCode(this.signUser.phone).then(() => {
        this.$message.success('验证码发送成功')
      })
      this.timeOut = Date.now() + 1000 * 60
    },
    // 倒计时结束
    onFinished () {
      this.timeOut = null
    },
    // 响应密码输入变化
    handlePasswordChange ($event) {
      this.passwordLevel = FieldRules.passwordLevel($event.target.value)
    },
    // 响应登录
    handleSignIn () {
      this.$router.push({ name: 'signIn' })
    },
    // 响应注册
    handleSignUp () {
      this.loading = true
      // 校验
      this.$refs.signForm.validate().then(() => {
        // 注册
        this.$api.system.signUp(this.signUser).then(() => {
          // 注册成功提示并跳转
          this.$message.success(`用户【${this.signUser.account}】帐号注册成功！`)
          this.$router.replace({ name: 'signIn' })
        }).catch(() => this.loading = false)
      }).catch(() => this.loading = false)
    }
  }
}
</script>
