<template>
  <a-radio-group v-if="options"
                 class="atom-icon-radio"
                 :name="name"
                 :value="modelValue"
                 :disabled="disabled"
                 @change="handleChange">
    <template v-for="option in options" :key="option.value">
      <a-radio :value="option.value"
               :checked="option.value === modelValue"
               :autofocus="option.autofocus || false">
        <a-tooltip :title="option.title">
          <!-- 图标存在时显示图标，否则显示value值 -->
          <IconFont v-if="option.icon" :style="iconStyle" :type="option.icon"/>
          <template v-else>{{ option.title }}</template>
        </a-tooltip>
      </a-radio>
    </template>
  </a-radio-group>
</template>

<script>
// size的默认值
const sizeEnum = {
  large: 58,
  default: 48,
  small: 38
}
/**
 * 图标单选
 */
export default {
  name: 'IconRadio',
  props: {
    // radio选择组件的值
    modelValue: {
      type: [String, Number, Object]
    },
    // 单选选项 [{type, icon, title, value, autofocus, checked}]
    options: {
      type: Array
    },
    // radio的name
    name: {
      type: String,
      default: 'iconRadio'
    },
    // 大小 large | default | small
    size: {
      type: String,
      default: 'default'
    },
    // 是否禁用
    disabled: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    // 自定义图标样式
    iconStyle () {
      let fontSize = 18
      if (this.$utils.isValid(sizeEnum[this.size])) {
        fontSize = sizeEnum[this.size]
      } else if (Number.isInteger(this.size)) {
        fontSize = this.size
      }
      return { fontSize: fontSize + 'px' }
    }
  },
  emits: ['update:modelValue', 'change'],
  methods: {
    // 响应radio的值变化
    handleChange (event) {
      this.$emit('update:modelValue', event.target.value)
      this.$emit('change', event.target.value)
    }
  }
}
</script>

<style lang="less">
.atom-icon-radio {
  display: flex !important;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
  .ant-radio-wrapper {
    display: flex;
    flex-direction: column-reverse;
    align-items: center;
  }
}
</style>
