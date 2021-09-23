<template>
  <a-checkbox v-model:checked="labelShow" class="atom-option-tree-checkbox">是否显示标签</a-checkbox>
  <a-radio-group v-model:value="selected" class="atom-option-tree">
    <OptionNode v-for="option in options"
                :key="option.key"
                :defaultValue="(modelValue||[]).slice(1)"
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
    // 默认选中值，双绑
    modelValue: {
      type: [Array, String, Number]
    },
    // 选项列表双绑[title][value]
    options: {
      type: Array,
      default: () => ([])
    },
    // label与value是否一样
    labelDiff: {
      type: Boolean,
      default: false
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
      labelShow: false
    }
  },
  emits: ['update:modelValue'],
  watch: {
    // 监听外部默认选中值绑定
    modelValue: {
      deep: true,
      immediate: true,
      handler (newValue) {
        newValue = (newValue && [...newValue]) || []
        this.selected = newValue[0] || ''
      }
    },
    // 监听外部选项绑定
    options: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.initOptionTree(newValue)
      }
    },
    // 监听外部label是否一致的绑定
    labelDiff (newValue) {
      this.labelShow = newValue
    },
    // 监听内部label是否一致的变化
    labelShow (newValue) {
      this.$emit('update:labelDiff', newValue)
    }
  },
  methods: {
    // 初始化选项树
    initOptionTree (options) {
      options.forEach(node => {
        node.key = node.key || node.value
        if (this.$utils.isValid(node.children)) {
          this.initOptionTree(node.children)
        }
      })
    }
  }
}
</script>

<style lang="less">
.atom-option-tree-checkbox {
  margin-bottom: 12px;
}
.atom-option-tree {
  .ant-radio-wrapper {
    display: inline-flex;
    align-items: center;
    margin-bottom: 6px;
    margin-right: 0;
    span.ant-radio + * {
      padding-right: 0;
    }
    .atom-option-node {
      .ant-input-group-addon {
        padding: 0 4px;
        background-color: unset;
        .anticon {
          padding: 4px;
          font-size: 18px;
          color: @primary-color;
          &:hover {
            border-radius: 50%;
            transition: all 0.3s;
            background-color: @primary-color;
            color: #FFFFFF;
          }
          &.anticon-minus-circle {
            color: @error-color;
            &:hover {
              background-color: @error-color;
              color: #FFFFFF;
            }
          }
        }
      }
    }
  }
  &.child {
    margin-left: 12px;
    max-height: 144px;
    overflow: auto;
    &>.ant-radio-wrapper:last-child {
      margin-bottom: 0;
    }
  }
}
</style>
