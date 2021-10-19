<template>
  <a-form-item :key="widget.key">
    <!-- 栅格布局 -->
    <a-row v-if="isType('grid')"
           :align="widget.options.align"
           :gutter="[widget.options.gutter, 24]"
           :justify="widget.options.justify"
           :wrap="widget.options.wrap"
           :style="widget.options.style">
      <a-col v-for="column in widget.columns"
             :key="column.order"
             :span="column.span"
             :order="column.order">
        <InnerForm :item="column" :size="size" :curWidget="curWidget"
                   @maker-widget-change="handleWidgetChange"></InnerForm>
      </a-col>
    </a-row>
    <!-- 表格布局 -->
    <table v-else-if="isType('table')" :style="widget.options.style">
      <tr v-for="row in widget.rows" :key="row.key">
        <td v-for="column in row.columns" :key="column.key">
          <InnerForm :item="column" :size="size" :curWidget="curWidget"
                     @maker-widget-change="handleWidgetChange"></InnerForm>
        </td>
      </tr>
    </table>
    <!-- 标签页布局 -->
    <a-tabs v-else-if="isType('tab')"
            :activeKey="(widget.options && widget.options.tabs && widget.options.tabs.default[0]) || 0"
            :type="widget.options.tabType"
            :tabPosition="widget.options.tabPosition"
            :style="widget.options.style">
      <a-tab-pane v-for="item in widget.tabs"
                  :key="item.key"
                  :tab="item.tab">
        <InnerForm :item="item" :size="size" :curWidget="curWidget"
                   @maker-widget-change="handleWidgetChange"></InnerForm>
      </a-tab-pane>
    </a-tabs>
    <!-- 分步布局 -->
    <a-steps v-else-if="isType('step')">
      <a-step></a-step>
    </a-steps>
    <!-- 描述布局 -->
    <a-descriptions v-else-if="isType('desc')">
      <a-descriptions-item></a-descriptions-item>
    </a-descriptions>
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
/**
 * 内部表单组件
 */
const InnerForm = {
  name: 'InnerForm',
  components: { FormWidget },
  mixins: [copy],
  props: {
    // 组件组
    item: {
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
    // 响应组件的增加
    handleWidgetAdd (event, item) {
      // 设置当前操作的组件
      this.$emit('maker-widget-change', item.widgets[event['newDraggableIndex']])
    },
    // 响应组件选择改变
    handleWidgetChange (curWidget) {
      this.$emit('maker-widget-change', curWidget)
    },
    // 响应组件的复制操作
    handleWidgetCopy (item, widget, index) {
      this.$emit('maker-widget-change', this.widgetCopy(widget, index, item.widgets))
    },
    // 响应组件的删除操作
    handleWidgetDelete (item, widget, index) {
      item.widgets.splice(index, 1)
      this.$emit('maker-widget-change', item.widgets[index - 1] || {})
    }
  },
  template: `
    <div class="atom-maker-inner-form">
      <Draggable v-bind="dragOptions"
                 v-model="item.widgets"
                 itemKey="key"
                 tag="transition-group"
                 :component-data="{ name: 'fade' }"
                 @add="handleWidgetAdd($event, item)">
        <template #item="{ element, index }">
          <div :class="['atom-maker-item', element.key === curWidget.key ? 'active' : '']"
               @click.stop="handleWidgetChange(element)">
            <FormWidget :widget="element"
                        :size="size"></FormWidget>
            <div class="atom-maker-actions" v-if="element.key === curWidget.key">
              <a-tooltip title="复制">
                <a-button type="primary" size="small" @click.stop="handleWidgetCopy(item, element, index)"><IconFont type="CopyOutlined"/></a-button>
              </a-tooltip>
              <a-tooltip title="删除">
                <a-button type="primary" size="small" danger @click.stop="handleWidgetDelete(item, element, index)"><IconFont type="DeleteOutlined"/></a-button>
              </a-tooltip>
            </div>
          </div>
        </template>
      </Draggable>
    </div>
  `
}
export default {
  name: 'LayoutWidget',
  components: { InnerForm },
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
  emits: ['maker-widget-change'],
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.widget.type || 'text')
    },
    // 响应组件选择改变
    handleWidgetChange (curWidget) {
      this.$emit('maker-widget-change', curWidget)
    },
  }
}
</script>
