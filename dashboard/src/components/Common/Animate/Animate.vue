<template>
  <transition-group v-if="!disabled"
              :enter-active-class="enterClass"
              :leave-active-class="leaveClass">
    <slot></slot>
  </transition-group>
  <slot v-else></slot>
</template>

<script>
/**
 * 动画效果
 */
// 引入animate.css样式库
import 'animate.css'
import { animateSet, directionSet } from './config'
export default {
  name: 'Animate',
  props: {
    // 是否禁用
    disabled: {
      type: Boolean,
      default: false
    },
    // 动画
    animate: {
      type: String,
      validator(value) {
        return animateSet[value]
      },
      default: 'slide'
    },
    // 动画方向
    direction: {
      type: String,
      validator (value) {
        return directionSet[value]
      },
      default: 'Left'
    }
  },
  data () {
    return {
      // 动画效果前缀
      animatePrefix: 'animate__',
      // 基础动画
      baseClass: 'atom-animate animate__animated'
    }
  },
  computed: {
    // 动画样式
    animateClass () {
      return this.generateClass()
    },
    // 进入样式
    enterClass () {
      return [...this.animateClass.In].join(' ')
    },
    // 退出样式
    leaveClass () {
      return [...this.animateClass.Out].join(' ')
    }
  },
  methods: {
    // 生成样式
    generateClass () {
      // 选中的动画
      const animate = animateSet[this.animate]
      // 默认是动画进入的效果，退出的效果自动根据相反方向来确定
      const directionIn = this.direction
      const directionOut = animate.directionOuts[animate.directionIns.indexOf(directionIn)]
      return {
        In: [this.baseClass, this.animatePrefix.concat(this.animate, directionSet.In, directionIn)],
        Out: [this.baseClass, this.animatePrefix.concat(this.animate, directionSet.Out, directionOut)]
      }
    }
  }
}
</script>

