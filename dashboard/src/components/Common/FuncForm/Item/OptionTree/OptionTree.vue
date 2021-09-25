<template>
  <a-checkbox v-model:checked="labelShow" class="atom-option-tree-checkbox">是否显示标签</a-checkbox>
  <a-radio-group class="atom-option-tree"
                 v-model:value="selected"
                 @change="handleRadioChange">
    <a-collapse v-if="cascade" v-model:activeKey="activeKey" :bordered="false" accordion>
      <OptionNode v-for="(option, index) in optionsTree"
                  :key="option.key"
                  :optionIndex="index"
                  :defaultValue="defaultValue.slice(1)"
                  :pSelected="selected"
                  :option="option"
                  :labelShow="labelShow"
                  :size="size"
                  :cascade="cascade"
                  @option-selected-change="handleSelectedChange"
                  @option-delete="handleOptionDelete"
                  @option-add="handleOptionAdd"></OptionNode>
    </a-collapse>
    <OptionNode v-else
                v-for="(option, index) in optionsTree"
                :key="option.key"
                :optionIndex="index"
                :defaultValue="defaultValue.slice(1)"
                :pSelected="selected"
                :option="option"
                :labelShow="labelShow"
                :size="size"
                :cascade="cascade"
                @option-selected-change="handleSelectedChange"
                @option-delete="handleOptionDelete"
                @option-add="handleOptionAdd"></OptionNode>
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
    // 是否多级
    cascade: {
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
      labelShow: false,
      // 默认值
      defaultValue: [],
      // 选项树结构
      optionsTree: [],
      // 当前激活的窗口，默认为选中的第一个，或者没有任何选中时的第一个选项
      activeKey: 0
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
        // 默认为选中的第一个，或者没有任何选中时的第一个选项
        this.activeKey = this.defaultValue[0] || this.optionsTree[0].value
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
        node.label = node.title
        if (this.$utils.isValid(node.children)) {
          this.initOptionTree(node.children)
        }
      })
    },
    // 响应选项删除
    handleOptionDelete (index) {
      this.optionsTree.splice(index, 1)
    },
    // 响应选项同级增加
    handleOptionAdd (index) {
      // 设置新元素的key，保障惟一性
      const key = this.$utils.randomStr(6)
      const addOption = { key: key,  value: key, title: this.labelShow ? '选项' : key, label: this.labelShow ? '选项' : key }
      this.optionsTree.splice(index + 1, 0, addOption)
    },
    // 响应radio值改变
    handleRadioChange () {
      this.defaultValue = [this.selected]
      this.defaultOptions.default = this.defaultValue
    },
    // 响应子集默认选中值的改变
    handleSelectedChange (selected, len, optionNode) {
      this.defaultValue.splice(1, len, ...selected)
      // 如果是下级传到顶级，则根据下级的optionNode改变顶层的默认值
      if (optionNode) {
        this.defaultValue[0] = optionNode.value
      }
      this.defaultOptions.default = this.defaultValue
    }
  }
}
</script>

<style lang="less">
@import "optionTree";
</style>
