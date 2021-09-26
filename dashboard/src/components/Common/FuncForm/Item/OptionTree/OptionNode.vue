<template>
  <!-- 多级 -->
  <a-collapse-panel v-if="cascade" :showArrow="false" :key="optionNode.value">
    <template #header>
      <!-- @click.stop防止展开事件多次触发 -->
      <a-radio v-if="cascade" :value="optionNode.value" @click.stop>
        <a-input-group :size="size" compact class="atom-option-node">
          <a-input v-if="labelShow"
                   placeholder="label"
                   v-model:value="optionNode.title"
                   @change="handleTitleChange"
                   :style="{ width: '40%' }"/>
          <a-input placeholder="value"
                   v-model:value="optionNode.value"
                   @change="handleValueChange"
                   :style="{ width: labelShow ? '60%' : '100%' }">
            <template #addonAfter>
              <IconFont type="MinusCircleOutlined"
                        @click="handleAction( { key: 'delete' }, optionIndex, optionNode)"/>
              <a-divider type="vertical" />
              <a-dropdown :trigger="['hover', 'click']">
                <IconFont type="PlusCircleOutlined"/>
                <template #overlay>
                  <a-menu @click="handleAction($event, optionIndex, optionNode)">
                    <a-menu-item key="add">同级增加</a-menu-item>
                    <a-menu-item key="addChild">子级增加</a-menu-item>
                  </a-menu>
                </template>
              </a-dropdown>
            </template>
          </a-input>
        </a-input-group>
      </a-radio>
    </template>
    <!-- 下级 -->
    <a-radio-group v-if="optionNode.children && optionNode.children.length > 0"
                   class="atom-option-tree child"
                   :name="optionNode.value"
                   v-model:value="selected"
                   @change="handleRadioChange">
      <a-collapse v-if="cascade" v-model:activeKey="activeKey" :bordered="false">
        <OptionNode v-for="(child, index) in optionNode.children"
                    :key="child.key"
                    :optionIndex="index"
                    :defaultValue="defaultValue.slice(1)"
                    :pSelected="selected"
                    :option="child"
                    :labelShow="labelShow"
                    :size="size"
                    :cascade="cascade"
                    @option-selected-change="handleSelectedChange"
                    @option-delete="handleOptionDelete($event, optionNode.children)"
                    @option-add="handleOptionAdd($event, optionNode.children)"></OptionNode>
      </a-collapse>
    </a-radio-group>
  </a-collapse-panel>
  <!-- 单级 -->
  <a-radio v-else :value="optionNode.value">
    <a-input-group :size="size" compact class="atom-option-node">
      <a-input v-if="labelShow"
               placeholder="label"
               v-model:value="optionNode.title"
               :style="{ width: '40%' }"/>
      <a-input placeholder="value"
               v-model:value="optionNode.value"
               :style="{ width: labelShow ? '60%' : '100%' }">
        <template #addonAfter>
          <IconFont type="MinusCircleOutlined"
                    @click="handleAction( { key: 'delete' }, optionIndex, optionNode)"/>
          <a-divider type="vertical" />
          <IconFont type="PlusCircleOutlined"
                    @click="handleAction( { key: 'add' }, optionIndex, optionNode)"/>
        </template>
      </a-input>
    </a-input-group>
  </a-radio>
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
    // 是否多级
    cascade: {
      type: Boolean,
      default: false
    },
    // 父级选中的值
    pSelected: {
      type: [String, Number]
    },
    // 选项序列
    optionIndex: {
      type: Number,
      default: 0
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
      selected: '',
      // 当前激活的窗口
      activeKey: 0
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
      // 展开选中的
      if (this.cascade) {
        this.activeKey = this.selected
      }
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
  emits: ['option-delete', 'option-add', 'option-selected-change'],
  methods: {
    // 响应操作
    handleAction (action, index, option) {
      if (action.key === 'delete') {
        // 删除当前行
        this.$emit('option-delete', index, option)
      } else if (action.key === 'add') {
        // 同级增加
        this.$emit('option-add', index, option)
      } else if (action.key === 'addChild') {
        // 设置新元素的key，保障惟一性
        const key = this.$utils.randomStr(6)
        const addOption = { key: key,  value: key, title: this.labelShow ? '选项' : key, label: this.labelShow ? '选项' : key }
        // 增加子级
        if (option.children) {
          option.children.push(addOption)
        } else {
          option.children = [addOption]
        }
        // 防止下级菜单还未生成导致的展开错误
        this.$nextTick(() => {
          this.activeKey = option.value
        })
      }
    },
    // 响应选项删除
    handleOptionDelete (index, options) {
      options.splice(index, 1)
    },
    // 响应选项同级增加
    handleOptionAdd (index, options) {
      // 设置新元素的key，保障惟一性
      const key = this.$utils.randomStr(6)
      const addOption = { key: key,  value: key, title: this.labelShow ? '选项' : key, label: this.labelShow ? '选项' : key }
      options.splice(index + 1, 0, addOption)
    },
    // 响应radio值的改变
    handleRadioChange () {
      // 如果是多级，选中时展开当前级别
      if (this.cascade) {
        this.activeKey = this.optionNode.value
      }
      // 当前层改变，每往上一层index+1，相当于从后往前推选中值
      this.$emit('option-selected-change', [this.selected], 1, this.optionNode)
    },
    // 响应radio选中的改变
    handleSelectedChange (selected, len, option) {
      // 如果是多级，选中时展开当前级别
      if (this.cascade) {
        this.activeKey = option.value
      }
      // 如果子级的radio改变，且值为非空，则本级必然选中为他自己
      this.$emit('option-selected-change', [option.value, ...selected], len + 1, this.optionNode)
    },
    // 响应title的变化，用于更新label作为兼容
    handleTitleChange (event) {
      this.optionNode.label = event.target.value
    },
    // 响应value的变化，用于更新label作为兼容
    handleValueChange (event) {
      this.optionNode.label = event.target.value
    }
  }
}
</script>
