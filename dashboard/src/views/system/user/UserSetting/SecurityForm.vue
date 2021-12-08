<template>
  <a-card id="security" title="安全设置">
    <FormList ref="userForm"
              v-model="sysUser"
              :fields="fields"
              :labelCol="{ style: 'width: 80px' }"
              @submit="handleFormSubmit">
      <template #footer="{ handleClick }">
        <a-button @click="handleClick" type="primary" block :loading="loading">更新登录密码</a-button>
      </template>
    </FormList>
  </a-card>
</template>

<script>/**
 * 个人信息设置-基础信息
 */
import { FormList } from '@/components/Advance/FuncForm'
export default {
  name: 'SecurityForm',
  components: { FormList },
  props: {
    // 用户信息
    userInfo: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      // 用户信息
      sysUser: {},
      // 安全设置字段
      fields: [
        { type: 'password', name: 'originPassword', label: '原密码', rules: [{ required: true }] },
        { type: 'password', name: 'password', label: '新密码', rules: [{ type: 'password', required: true }] },
        {
          type: 'password',
          name: 'confirmPassword',
          label: '确认密码',
          rules: [{ validator: async (rule, value) => {
              if (value === '' || value !== this.sysUser.password) {
                return Promise.reject('确认密码必须与新密码一致')
              } else {
                return Promise.resolve()
              }
            }, required: true }]
        }
      ],
      // 是否加载中
      loading: false
    }
  },
  methods: {
    // 表单提交
    handleFormSubmit (sysUser) {
      this.loading = true
      this.$refs.userForm.validate().then(() => {
        this.$api.system.user.updatePassword(this.userInfo.id, sysUser).then(() => {
          this.$message.success('安全密码更新成功')
        }).finally(() => { this.loading = false })
      }).catch(() => { this.loading = false })
    }
  }
}
</script>
