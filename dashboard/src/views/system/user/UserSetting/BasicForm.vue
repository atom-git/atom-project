<template>
  <a-card id="basic" title="基本信息">
    <a-row>
      <a-col :xs="{ span: 24, order: 1 }" :md="{ span: 12, order: 0}">
        <FormList ref="userForm"
                  v-model="sysUser"
                  :fields="fields"
                  :hiddenFooter="true"
                  :labelCol="{ style: 'width: 80px' }"></FormList>
      </a-col>
      <a-col :xs="{ span: 24, order: 0 }" :md="{ span: 12, order: 1}">
        <ImagePicker v-model="sysUser.head" :headCutter="true" imgOutType="png"></ImagePicker>
      </a-col>
    </a-row>
    <a-button @click="handleClick" type="primary" block :loading="loading">更新个人信息</a-button>
  </a-card>
</template>

<script>
/**
 * 个人信息设置-基础信息
 */
import { FormList } from '@/components/Advance/FuncForm'
import { ImagePicker } from '@/components/Common/FormItem'
const division = require('/public/import/division.json')
export default {
  name: 'BasicForm',
  components: { FormList, ImagePicker },
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
      // 用户信息字段
      fields: [
        { type: 'input', name: 'phone', label: '手机', rules: [{ type: 'mobile', required: true }] },
        { type: 'input', name: 'email', label: '邮箱', rules: [{ type: 'email', required: true }] },
        { type: 'input', name: 'name', label: '昵称', rules: [{ required: true }] },
        { type: 'textarea', name: 'motto', label: '格言' },
        { type: 'cascader', name: 'location', label: '位置', options: division, replaceFields: { value: 'code', label: 'name', children: 'children' } }
      ],
      // 是否加载中
      loading: false
    }
  },
  mounted () {
    // 初始化时对绑定的用户信息进行初始化
    this.sysUser = this.userInfo
  },
  methods: {
    // 响应个人信息更新
    handleClick () {
      this.loading = true
      this.$refs.userForm.validate().then(() => {
        this.$api.system.user.update(this.sysUser).then(() => {
          this.$message.success('个人信息更新成功')
        })
      }).finally(() => { this.loading = false })
    }
  }
}
</script>
