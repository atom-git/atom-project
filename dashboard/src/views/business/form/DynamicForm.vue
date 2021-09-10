<template>
  <FormList title="文件上传功能测试"
            :fields="fields"
            v-model="model"
            @submit="handleSubmit"></FormList>
</template>

<script>
import { FormList } from '@/components/Common/FuncForm'
export default {
  name: 'DynamicForm',
  components: { FormList },
  data () {
    return {
      fields: [
        { name: 'fileUpload', label: 'FileUpload', type: 'fileUpload', fileType: 'file', multiple: true, max: 2 }
      ],
      model: {}
    }
  },
  methods: {
    // 响应提交
    handleSubmit (model) {
      console.log(model)
    }
  }
}
</script>
