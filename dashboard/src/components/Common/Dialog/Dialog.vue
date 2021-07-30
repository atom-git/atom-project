<template>
  <a-modal v-if="type === 'modal'"
           v-bind="$attrs"
           class="atom-dialog"
           :width="width"
           v-model:visible="visible"
           :confirmLoading="loading"
           :footer="footer">
    <!-- 外部自定义的slot传入 -->
    <template v-for="slotName in Object.keys($slots)">
      <slot :name="slotName"></slot>
    </template>
  </a-modal>
  <a-drawer v-else
            v-bind="$attrs"
            class="atom-dialog"
            :width="width"
            v-model:visible="visible"
            @close="$emit('cancel')">
    <!-- title挂载点 -->
    <template v-if="Object.keys($slots).includes('title')">
      <slot name="title"></slot>
    </template>
    <!-- handle挂载点 -->
    <template v-if="Object.keys($slots).includes('handle')">
      <slot name="handle"></slot>
    </template>
    <!-- 内容部分挂载点 -->
    <div class="atom-dialog-content">
      <slot></slot>
    </div>
    <div class="atom-dialog-footer">
      <!-- 如果外部有传入footer，则使用外部传入slot，否则根据footer属性判断 -->
      <template v-if="Object.keys($slots).includes('footer')">
        <slot name="footer"></slot>
      </template>
      <!-- 如果footer为空，则不显示footer，否则默认为取消，确认两个按钮 -->
      <template v-else-if="!footer">
        <a-button @click="() => { this.visible = false; $emit('cancel') }">{{ locale.Modal.cancelText }}</a-button>
        <a-button type="primary" :loading="loading" @click="$emit('ok')">{{ locale.Modal.okText }}</a-button>
      </template>
    </div>
  </a-drawer>
</template>

<script>
import Default from '@/config/default'
export default {
  name: 'Dialog',
  props: {
    // 弹窗类型 modal 或者 drawer
    type: {
      type: String,
      default: Default.dialogType
    },
    // 用于控制显隐
    modelValue: {
      type: Boolean,
      default: false
    },
    // 弹窗的宽度
    width: {
      type: [String, Number],
      default: Default.dialogSize
    },
    // 底部
    footer: {
      type: String,
      required: false
    },
    // 确认后的loading
    loading: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      // 弹窗显示与否
      visible: false
    }
  },
  computed: {
    // locale信息用于命名按钮
    locale () {
      return this.$root.locale
    }
  },
  watch: {
    // 监听外部传入的显示与否
    modelValue (newValue) {
      this.visible = newValue
    },
    // 监听内部的
    visible (newValue) {
      this.$emit('update:modelValue', newValue)
    }
  }
}
</script>

<style lang="less">
@import "dialog";
</style>
