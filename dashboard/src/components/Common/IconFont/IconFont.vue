<template>
  <!-- 判断如果是atom-开头的图标，则直接使用iconFont图标体系，否则使用原生图标，统一两个图标集，方便组件使用 -->
  <SelfIcon v-if="type && type.startsWith('atom-')"
            :type="type"
            :spin="spin"
            :rotate="rotate" v-bind="$attrs"></SelfIcon>
  <component v-else-if="type" :is="$antIcons[type]" :spin="spin" :rotate="rotate" v-bind="$attrs" />
</template>

<script>
/**
 * antv icon和 iconfont图标统一组件
 * iconfont组件需以atom-开头
 */
import { createFromIconfontCN } from '@ant-design/icons-vue'
const SelfIcon = typeof window === 'undefined' ? null : createFromIconfontCN({
  scriptUrl: require('./iconfont.js'),
  extraCommonProps: {}
})
export default {
  name: 'IconFont',
  components: {
    SelfIcon
  },
  props: {
    type: {
      type: String,
      default: null
    },
    // 是否旋转
    spin: {
      type: Boolean,
      default: false
    },
    // 图标旋转的角度
    rotate: {
      type: Number
    }
  }
}
</script>
