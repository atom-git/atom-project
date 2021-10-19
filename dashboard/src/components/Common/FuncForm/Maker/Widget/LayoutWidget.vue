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
        <div class="atom-maker-inner-form">
          <Draggable v-bind="dragOptions"
                     v-model="column.widgets"
                     itemKey="key"
                     tag="transition-group"
                     :component-data="{ name: 'fade' }"
                     @add="handleWidgetAdd($event, column)">
            <!-- FormItem渲染 -->
            <template #item="{ element, index }">
              <div :class="['atom-maker-item', element.key === curWidget.key ? 'active' : '']"
                   @click.stop="handleWidgetChange(element)">
                <!-- 布局元素中不允许出现布局元素 -->
                <!-- form组件元素 -->
                <FormWidget :widget="element"
                            :size="size"></FormWidget>
                <!-- 当前选中组件时显示复制删除按钮 -->
                <div class="atom-maker-actions" v-if="element.key === curWidget.key">
                  <a-tooltip title="复制">
                    <a-button type="primary" size="small" @click.stop="handleWidgetCopy(column, element, index)"><IconFont type="CopyOutlined"/></a-button>
                  </a-tooltip>
                  <a-tooltip title="删除">
                    <a-button type="primary" size="small" danger @click.stop="handleWidgetDelete(column, element, index)"><IconFont type="DeleteOutlined"/></a-button>
                  </a-tooltip>
                </div>
              </div>
            </template>
          </Draggable>
        </div>
      </a-col>
    </a-row>
    <!-- 表格布局 -->
    <a-table v-else-if="isType('table')"></a-table>
    <!-- 默认为文本布局 -->
    <div v-else>{{ widget.options.default }}</div>
  </a-form-item>
</template>

<script>
/**
 * 布局构造器
 */
import FormWidget from './FormWidget'
import { message } from 'ant-design-vue'
import copy from '../mixins/copy'
export default {
  name: 'LayoutWidget',
  components: { FormWidget },
  mixins: [copy],
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
    },
    // 当前操作的组件
    curWidget: {
      type: Object,
      required: false
    }
  },
  data () {
    return {
      // 拖动配置
      dragOptions: {
        animation: 300,
        group: {
          name: 'widgets',
          put: function (to, from, target) {
            // 如果是布局类型，则无法插入至其中
            if (target.type === 'layout') {
              message.warn({ key: 'message-warn', content: '无法在布局组件中加入布局组件！' })
            }
            return from.options.group && from.options.group.name === 'toolboxs' && target.type !== 'layout'
          }
        },
        sort: true,
        ghostClass: 'atom-widget-ghost'
      }
    }
  },
  emits: ['maker-widget-change'],
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.widget.type || 'text')
    },
    // 响应组件的增加
    handleWidgetAdd (event, column) {
      // 设置当前操作的组件
      this.$emit('maker-widget-change', column.widgets[event['newDraggableIndex']])
    },
    // 响应组件选择改变
    handleWidgetChange (curWidget) {
      this.$emit('maker-widget-change', curWidget)
    },
    // 响应组件的复制操作
    handleWidgetCopy (column, widget, index) {
      this.$emit('maker-widget-change', this.widgetCopy(widget, index, column.widgets))
    },
    // 响应组件的删除操作
    handleWidgetDelete (column, widget, index) {
      column.widgets.splice(index, 1)
      this.$emit('maker-widget-change', column.widgets[index - 1] || {})
    }
  }
}
</script>
