<template>
  <a-form-item :key="widget.key">
    <!-- 栅格布局 -->
    <a-row v-if="isType('grid')"
           :align="widget.options.align"
           :gutter="[widget.options.gutter, 24]"
           :justify="widget.options.justify"
           :wrap="widget.options.wrap"
           :style="widget.options.style">
      <!-- 有元素时 -->
      <a-col v-for="column in widget.columns"
             :key="column.order"
             :span="column.span"
             :order="column.order">
        <Draggable v-bind="dragOptions"
                   :list="column.widgets"
                   itemKey="key"
                   tag="transition-group"
                   :component-data="{ name: 'fade' }"
                   @add="handleWidgetAdd($event, col)">
          <!-- FormItem渲染 -->
          <template #item="{ element }">
            <div :class="['atom-maker-item', element.key === curWidget.key ? 'active' : '']">
              <!-- 布局元素中不允许出现布局元素 -->
              <!-- form组件元素 -->
              <FormWidget :widget="element"
                          :size="size"
                          @click="handleWidgetChange(element)"></FormWidget>
            </div>
          </template>
        </Draggable>
      </a-col>
    </a-row>
    <!-- 默认为文本布局 -->
    <div v-else>{{ widget.options.default }}</div>
  </a-form-item>
</template>

<script>
/**
 * 布局构造器
 */
import FormWidget from './FormWidget'
export default {
  name: 'LayoutWidget',
  components: { FormWidget },
  props: {
    // 组件
    widget: {
      type: Object,
      required: true
    },
    // 组件尺寸
    size: {
      type: String,
      default: 'default'
    }
  },
  data () {
    return {
      // 当前操作的组件
      curWidget: {},
      // 拖动配置
      dragOptions: {
        animation: 300,
        group: {
          name: 'widgets',
          put: function (to, from, target) {
            // 如果是布局类型，则无法插入至其中
            return target.type !== 'layout'
          }
        },
        sort: true,
        ghostClass: 'atom-widget-ghost'
      }
    }
  },
  emits: ['maker-widget-change'],
  // 禁用继承
  inheritAttrs: false,
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.widget.type || 'text')
    },
    // 响应组件的增加
    handleWidgetAdd (event, col) {
      // 设置当前操作的组件
      this.curWidget = col.widgets[event['newDraggableIndex']]
      this.$emit('maker-widget-change', this.curWidget)
    },
    // 响应组件选择改变
    handleWidgetChange (curWidget) {
      this.curWidget = curWidget
      this.$emit('maker-widget-change', this.curWidget)
    }
  }
}
</script>
