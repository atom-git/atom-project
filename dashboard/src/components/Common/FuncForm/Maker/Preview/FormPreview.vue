<template>
  <Dialog v-model="dialogVisible"
          :title="title"
          :maskClosable="false"
          :destroyOnClose="true"></Dialog>
</template>

<script>
/**
 * 表单制作预览
 */
import Dialog from '@/components/Common/Dialog'
export default {
  name: 'FormPreview',
  components: { Dialog },
  props: {
    // 用于控制显隐
    visible: {
      type: Boolean,
      default: false
    },
    // 表单标题
    title: {
      type: String,
      default: '表单预览'
    }
  },
  data () {
    return {
      // 弹窗是否显示
      dialogVisible: false
    }
  },
  watch: {
    // 监听外部visible变化
    visible (newValue) {
      this.dialogVisible = newValue
    }
  },
}
</script>
