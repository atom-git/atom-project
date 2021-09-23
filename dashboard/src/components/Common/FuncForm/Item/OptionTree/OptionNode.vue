<template>
  <a-radio :value="optionNode.value">
    <a-input-group :size="size" compact class="atom-option-node">
      <a-input v-if="labelShow"
               placeholder="label"
               v-model:value="optionNode.title"
               :style="{ width: '40%' }"/>
      <a-input placeholder="value"
               v-model:value="optionNode.value"
               :style="{ width: labelShow ? '60%' : '100%' }">
        <template #addonAfter>
          <IconFont type="MinusCircleOutlined" @click="handleAction('minus')"/>
          <a-divider type="vertical" />
          <a-dropdown :trigger="['hover', 'click']">
            <IconFont type="PlusCircleOutlined"/>
            <template #overlay>
              <a-menu @click="handleAction">
                <a-menu-item key="plus">同级增加</a-menu-item>
                <a-menu-item key="childPlus">子级增加</a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </template>
      </a-input>
    </a-input-group>
  </a-radio>
  <!-- 子选项 -->
  <template v-if="optionNode.children && optionNode.children.length > 0" >
    <a-radio-group :name="optionNode.value" v-model:value="selected" class="atom-option-tree child">
      <OptionNode v-for="child in optionNode.children"
                  :key="child.key"
                  :defaultValue="defaultValue.slice(1)"
                  :pSelected="selected"
                  :option="child"
                  :labelShow="labelShow"
                  :size="size"></OptionNode>
    </a-radio-group>
  </template>
</template>

<script>
/**
 * 选项节点
 */
export default {
  name: 'OptionNode',
  props: {
    // 默认值
    defaultValue: {
      type: Array,
      default: () => ([])
    },
    // 父级选中的值
    pSelected: {
      type: [String, Number]
    },
    // 选项列表双绑title|value|children
    option: {
      type: Object,
      default: () => ({})
    },
    // label与value是否一样
    labelShow: {
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
      // 选项配置信息
      optionNode: {},
      // 当前选中的节点
      selected: ''
    }
  },
  computed: {
    // 根据外部传入的默认值及上级选中值在计算当前层级选中值
    selectedValue () {
      return this.optionNode.value === this.pSelected ? this.defaultValue[0] || '' : ''
    }
  },
  watch: {
    // 根据选中值，给内部赋值
    selectedValue (newValue) {
      this.selected = newValue
    },
    // 监听内部选项配置变化
    option: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.optionNode = newValue
      }
    }
  },
  methods: {
    // 响应操作
    handleAction (action) {
      if (action === 'minus') {
        // 删除当前行
      } else if (action === 'plus') {
        // 同级增加
      } else if (action === 'plusChild') {
        // 增加子级
      }
    }
  }
}
</script>
