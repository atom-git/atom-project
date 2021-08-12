<template>
  <Dialog v-model="visible"
          :maskClosable="false"
          :closable="false"
          :footer="null">
    <!-- 多层级 -->
    <TypeCodeTree v-if="sysType.multiLevel === 1" :sysType="sysType"></TypeCodeTree>
    <!-- 单层级 -->
    <TypeCodeList v-if="sysType.multiLevel === 0" :sysType="sysType"></TypeCodeList>
    <!-- 底部关闭按钮 -->
    <template #footer>
      <a-button @click="() => { this.visible = false; $emit('cancel') }">关闭</a-button>
    </template>
  </Dialog>
</template>

<script>
/**
 * 数据字典维值管理
 */
import Dialog from '@/components/Common/Dialog'
import TypeCodeList from './TypeCodeList'
import TypeCodeTree from './TypeCodeTree'
export default {
  name: 'SysTypeCode',
  components: { Dialog, TypeCodeList, TypeCodeTree },
  props: {
    // 操作窗口显隐
    modelValue: {
      type: Boolean,
      default: false
    },
    // 数据字典信息
    sysType: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      // 请求loading
      loading: false,
      // dialog是否显示
      visible: false
    }
  },
  watch: {
    // 监听窗口显隐
    modelValue (newValue) {
      this.visible = newValue
    },
    // 监听内部窗口显隐
    visible (newValue) {
      this.$emit('update:modelValue', newValue)
    }
  }
}
</script>
