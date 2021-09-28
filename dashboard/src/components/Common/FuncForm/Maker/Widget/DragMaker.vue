<template>
  <Draggable v-bind="dragOptions"
             :list="widgets"
             itemKey="key"
             tag="transition-group"
             :component-data="{ name: 'fade' }"
             @add="handleWidgetAdd">
    <!-- FormItem渲染 -->
    <template #item="{ element }">
      <div :class="['atom-maker-item', element.key === curWidget.key ? 'active' : '']">
        <!-- 布局元素 -->
        <LayoutWidget v-if="element.group === 'layout'"
                      :widget="element"
                      :size="size"
                      @click="handleWidgetChange(element)"></LayoutWidget>
        <!-- form组件元素 -->
        <FormWidget v-else
                    :widget="element"
                    :size="size"
                    @click="handleWidgetChange(element)"></FormWidget>
      </div>
    </template>
  </Draggable>
</template>

<script>
import LayoutWidget from './LayoutWidget'
import FormWidget from './FormWidget'
export default {
  name: 'DragMaker',
  components: { LayoutWidget, FormWidget },
  props: {
    // 用于双绑的widgets
    modelValue: {
      type: Array
    },
    // 组件尺寸
    size: {
      type: String,
      default: 'default'
    }
  },
  data () {
    return {
      // 组件列表，从组件库中拖过来的信息，需要在结束后增加一个唯一性key
      widgets: [],
      // 当前操作的组件
      curWidget: {},
      // 拖动配置
      dragOptions: {
        animation: 300,
        group: { name: 'widgets' },
        sort: true,
        ghostClass: 'atom-widget-ghost'
      }
    }
  },
  watch: {
    // 监听外部传和的widgets变化
    modelValue: {
      deep: true,
      handler (newValue) {
        this.widgets = newValue
      }
    },
    // 监听内部widgets变化
    widgets: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
        this.$emit('change', newValue)
      }
    }
  },
  emits: ['update:modelValue', 'change', 'maker-widget-change'],
  // 禁用继承
  inheritAttrs: false,
  methods: {
    // 响应组件的增加
    handleWidgetAdd (event) {
      // 设置当前操作的组件
      this.curWidget = this.widgets[event['newDraggableIndex']]
      this.$emit('maker-widget-change', this.curWidget)
    },
    // 响应组件选择改变
    handleWidgetChange (widget) {
      // 设置当前操作的组件
      this.curWidget = widget
      this.$emit('maker-widget-change', this.curWidget)
    },
    // 响应组件子集的变化
    handleWidgetChildren (children, widget) {
      widget.children = children
    }
  }
}
</script>
