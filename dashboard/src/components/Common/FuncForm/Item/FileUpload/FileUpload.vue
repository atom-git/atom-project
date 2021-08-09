<template>
  文件上传器
</template>

<script>
/**
 * 文件上传器
 */
export default {
  name: 'FileUpload',
  props: {
    // 双绑的值
    value: {
      type: String,
      required: false
    },
    // 组件大小 large|default|small
    size: {
      type: String,
      default: 'default'
    }
  }
}
</script>
