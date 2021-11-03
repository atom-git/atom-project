<template>
  <Dialog v-model="dialogVisible"
          :title="title"
          :maskClosable="false"
          :destroyOnClose="true"
          :footer="null"
          @cancel="handleCancel">
    <!-- 表单区域 -->
    <a-form v-bind="formConfig" :style="{ width: `${formConfig.width}%` }">
      <template v-for="(widget, index) in widgets" :key="index">
        <!-- 布局元素 -->
        <LayoutPreview v-if="widget.group === 'layout'"
                      :widget="widget"
                       :labelCol="formConfig.labelCol"
                      :size="formConfig.size"></LayoutPreview>
        <!-- form组件元素 -->
        <FormWidget v-else
                    :widget="widget"
                    :labelCol="formConfig.labelCol"
                    :size="formConfig.size"></FormWidget>
      </template>
    </a-form>
  </Dialog>
</template>

<script>
/**
 * 表单制作预览
 */
import Dialog from '@/components/Common/Dialog'
import LayoutPreview from './LayoutPreview'
import FormWidget from '../Widget/FormWidget'
export default {
  name: 'MakerPreview',
  components: { Dialog, LayoutPreview, FormWidget },
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
    },
    // 表单配置
    formConfig: {
      type: Object,
      required: true
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
  watch: {
    // 监听外部visible变化
    visible (newValue) {
      this.dialogVisible = newValue
    }
  },
  emits: ['maker-preview-cancel'],
  methods: {
    // 响应预览取消
    handleCancel () {
      this.$emit('maker-preview-cancel')
    }
  }
}
</script>
