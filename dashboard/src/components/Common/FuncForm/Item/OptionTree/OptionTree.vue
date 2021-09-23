<template>
  <a-checkbox v-model:checked="labelShow" class="atom-option-tree-checkbox">是否显示标签</a-checkbox>
  <a-radio-group v-model:value="selected" class="atom-option-tree">
    <OptionNode v-for="option in optionsTree"
                :key="option.key"
                :defaultValue="defaultValue.slice(1)"
                :pSelected="selected"
                :option="option"
                :labelShow="labelShow"
                :size="size"></OptionNode>
  </a-radio-group>
</template>

<script>
/**
 * 选项树组件，用于多级选项配置，最大只支持四级，由于不能递归，因此仅能这么做
 */
import OptionNode from './OptionNode'
export default {
  name: 'OptionTree',
  components: { OptionNode },
  props: {
    // 默认选项双绑
    modelValue: {
      type: Object,
      default: () => ({})
    },
    // 组件大小 large|default|small
    size: {
      type: String,
      default: 'default'
    }
  },
  data () {
    return {
      // 当前选中的节点
      selected: '',
      // label与value是否一样
      labelShow: false,
      // 默认值
      defaultValue: [],
      // 选项树结构
      optionsTree: []
    }
  },
  computed: {
    // 构建内部的选项默认配置用于监听发起数据响应
    defaultOptions () {
      return {
        labelShow: this.labelShow,
        default: this.defaultValue,
        options: this.optionsTree
      }
    }
  },
  emits: ['update:modelValue', 'change'],
  watch: {
    // 监听外部选项绑定
    modelValue: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.labelShow = newValue.labelShow || false
        this.defaultValue = newValue.default || []
        this.selected = this.defaultValue[0] || ''
        this.initOptionTree(newValue.options)
        this.optionsTree = newValue.options
      }
    },
    // 响应内部options的变化
    defaultOptions: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
        this.$emit('change', newValue)
      }
    }
  },
  methods: {
    // 初始化选项树
    initOptionTree (options) {
      options.forEach(node => {
        node.key = node.key || node.value
        node.title = this.labelShow ? node.title || node.value : node.value
        if (this.$utils.isValid(node.children)) {
          this.initOptionTree(node.children)
        }
      })
    }
  }
}
</script>

<style lang="less">
@import "optionTree";
</style>
