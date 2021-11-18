<template>
  <Dialog v-model="dialogVisible"
          title="配置预览"
          :maskClosable="false"
          :destroyOnClose="true"
          :footer="null"
          @cancel="handleCancel">
    <!-- JSON格式预览区域 -->
    <JsonView :modelValue="previewJson" :expandDepth="3"></JsonView>
  </Dialog>
</template>

<script>
/**
 * 导出预览
 */
import Dialog from '@/components/Common/Dialog'
import JsonView from '@/components/Common/JsonView'
export default {
  name: 'JsonPreview',
  components: { Dialog, JsonView },
  props: {
    // 用于控制显隐
    visible: {
      type: Boolean,
      default: false
    },
    // 表单配置
    formConfig: {
      type: Object,
      default: () => ({})
    },
    // 组件清单
    widgets: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      // 弹窗是否显示
      dialogVisible: false
    }
  },
  computed: {
    // 预览的json数据
    previewJson () {
      return {
        formConfig: this.formConfig,
        widgets: this.widgets
      }
    }
  },
  watch: {
    // 监听外部visible变化
    visible (newValue) {
      this.dialogVisible = newValue
    }
  },
  emits: ['maker-export-cancel'],
  methods: {
    // 响应预览取消
    handleCancel () {
      this.$emit('maker-export-cancel')
    }
  }
}
</script>
