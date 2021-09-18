<template>
  <FormList title="文件上传功能测试"
            :fields="fields"
            v-model="model"
            @submit="handleSubmit"></FormList>
</template>

<script>
/**
 * 高级表单示例
 */
import { FormList } from '@/components/Common/FuncForm'
export default {
  name: 'AdvanceForm',
  components: { FormList },
  data () {
    return {
      fields: [
        { type: 'colorPicker', name: 'colorPicker1', label: 'colorPicker1', pickType: 'compact', default: '#9270CA' },
        { type: 'colorPicker', name: 'colorPicker2', label: 'colorPicker2', default: '#FFFFFF' },
        {
          name: 'fileUpload1',
          label: 'FileUpload2',
          type: 'fileUpload',
          fileType: 'file',
          multiple: true,
          max: 2
        },
        {
          name: 'fileUpload2',
          label: 'FileUpload2',
          type: 'fileUpload',
          fileType: 'file'
        },
        {
          name: 'tagCheck',
          label: 'tagCheck',
          type: 'tagCheck',
          options: [
              { title: 'tag1', value: 1, icon: 'AndroidOutlined' },
              { title: 'tag2', value: 2, icon: 'AndroidOutlined' },
              { title: 'tag3', value: 3, icon: 'AndroidOutlined' },
              { title: 'tag4', value: 4, icon: 'AndroidOutlined' },
              { title: 'tag5', value: 5, icon: 'AndroidOutlined' },
              { title: 'tag6', value: 6, icon: 'AndroidOutlined' },
              { title: 'tag7', value: 7, icon: 'AndroidOutlined' },
              { title: 'tag8', value: 8, icon: 'AndroidOutlined' },
              { title: 'tag9', value: 9, icon: 'AndroidOutlined' },
              { title: 'tag10', value: 10, icon: 'AndroidOutlined' },
              { title: 'tag11', value: 11, icon: 'AndroidOutlined' }
          ]
        },
        {
          name: 'multiTagCheck',
          label: 'multiTagCheck',
          type: 'tagCheck',
          multiple: true,
          options: [
            { title: 'tag1', value: 1, icon: 'AndroidOutlined' },
            { title: 'tag2', value: 2, icon: 'AndroidOutlined' },
            { title: 'tag3', value: 3, icon: 'AndroidOutlined' },
            { title: 'tag4', value: 4, icon: 'AndroidOutlined' },
            { title: 'tag5', value: 5, icon: 'AndroidOutlined' },
            { title: 'tag6', value: 6, icon: 'AndroidOutlined' },
            { title: 'tag7', value: 7, icon: 'AndroidOutlined' },
            { title: 'tag8', value: 8, icon: 'AndroidOutlined' },
            { title: 'tag9', value: 9, icon: 'AndroidOutlined' },
            { title: 'tag10', value: 10, icon: 'AndroidOutlined' },
            { title: 'tag11', value: 11, icon: 'AndroidOutlined' }
          ]
        }
      ],
      model: {
        fileUpload1: [
          { key: 2, url: '/file/advanceForm/报销单120210911155709669.pdf', name: '报销单1.pdf' },
          { key: 3, url: '/file/advanceForm/报销单220210911155709865.pdf', name: '报销单2.pdf' }
        ],
        tagCheck: [5],
        multiTagCheck: [1, 2]
      }
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
