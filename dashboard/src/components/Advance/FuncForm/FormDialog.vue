<template>
  <Dialog v-model="dialogVisible"
          :title="title"
          :width="dialogSize"
          :maskClosable="false"
          :loading="loading"
          :destroyOnClose="true"
          @ok="handleSubmit"
          @cancel="handleCancel">
    <!-- 表单提交后的异常信息显示 -->
    <a-form-item v-if="formError" class="atom-form-item">
      <a-alert :message="formError" type="error" banner show-icon />
    </a-form-item>
    <!-- 表单信息 -->
    <FormList ref="formList"
              v-model="model"
              :fields="fields"
              :bordered="false"
              hiddenFooter
              @submit="handleFormSubmit">
      <!-- 把外部传入的form slot传入内部 -->
      <template v-for="slotName in formSlots" #[slotName]="{ field, model }">
        <slot :name="slotName" :field="field" :model="model"></slot>
      </template>
    </FormList>
  </Dialog>
</template>

<script>
/**
 * 新增或者编辑的数据表单，默认实现
 */
import Dialog from '@/components/Common/Dialog'
import FormList from './FormList'
export default {
  name: 'UpdateForm',
  components: { Dialog, FormList },
  props: {
    // 用于控制显隐
    visible: {
      type: Boolean,
      default: false
    },
    // form表单的title
    title: {
      type: String,
      default: '数据表单'
    },
    // 弹窗宽度大小
    width: {
      type: Number,
      required: false
    },
    // 确认后的loading
    loading: {
      type: Boolean,
      default: false
    },
    // 表单数据项，用于绑定
    modelValue: {
      type: Object,
      default: () => ({})
    },
    // 表单字段项
    fields: {
      type: Array,
      default: () => [{}]
    },
    // 如果有slot必须指定其slot范围
    formSlots: {
      type: Array,
      required: false
    },
    // 服务端返回的异常错误
    formError: {
      type: String,
      required: false
    }
  },
  data () {
    return {
      // 表单的form值
      model: {},
      // 弹窗是否显示
      dialogVisible: false
    }
  },
  computed: {
    // 弹窗大小
    dialogSize () {
      return this.width || this.$store.getters.appConfig.dialog.size
    }
  },
  emits: ['update:modelValue', 'form-editor-submit', 'form-editor-cancel'],
  watch: {
    // 内部form表单变化时，提交变化
    model: {
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
      },
      deep: true
    },
    // 外部传入值的改变
    modelValue: {
      handler (newValue) {
        this.model = newValue
      },
      deep: true
    },
    // 监听外部visible变化
    visible (newValue) {
      this.dialogVisible = newValue
    }
  },
  methods: {
    // 响应提交
    handleSubmit () {
      this.$refs.formList.submitForm()
    },
    // 响应表单的数据提交
    handleFormSubmit (model) {
      this.$emit('form-editor-submit', model)
    },
    // 响应取消
    handleCancel () {
      this.$refs.formList.resetForm()
      this.$emit('form-editor-cancel')
    }
  }
}
</script>
