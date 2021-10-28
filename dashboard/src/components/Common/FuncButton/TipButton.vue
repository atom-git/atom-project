<template>
  <a-tooltip class="atom-tip-button">
    <template #title>
      <slot v-if="$slots.title" name="title"></slot>
      <template v-else-if="title">{{ title }}</template>
    </template>
    <a-button :type="type" :size="size" :disabled="disabled" @click="handleClick">
      <template #icon>
        <IconFont :type="icon" />
      </template>
      <slot/>
    </a-button>
  </a-tooltip>
</template>

<script>
/**
 * 具有tooltip的Button，slot:[title, default]
 * 具备tooltip及button的所有属性，默认展示形式为link
 */
export default {
  name: 'TipButton',
  props: {
    // 按钮的类型
    type: {
      type: String,
      default: 'link'
    },
    // title信息，可以使用slot进行挂载
    title: {
      type: String
    },
    // 图标
    icon: {
      type: String
    },
    // 按钮大小 [small default large]
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
  emits: ['click'],
  methods: {
    // 响应按钮点击
    handleClick () {
      this.$emit('click')
    }
  }
}
</script>
