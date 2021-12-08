<template>
  <!-- 标题布局 -->
  <FuncTitle v-if="isType('title')" :title="widget.options.title || '自定义标题'"></FuncTitle>
  <a-form-item v-else :key="widget.key">
    <!-- 栅格布局 -->
    <a-row v-if="isType('grid')"
           :align="widget.options.align"
           :gutter="[widget.options.gutter, 24]"
           :justify="widget.options.justify"
           :wrap="widget.options.wrap"
           :style="widget.options.style">
      <a-col v-for="column in widget.items"
             :key="column.order"
             :span="column.span"
             :order="column.order">
        <InnerForm :item="column" :labelCol="labelCol" :size="size" :curWidget="curWidget"
                   @maker-widget-change="handleWidgetChange"></InnerForm>
      </a-col>
    </a-row>
    <!-- 表格布局 -->
    <table v-else-if="isType('table')" :style="widget.options.style">
      <tr v-for="row in widget.items" :key="row.key">
        <td v-for="column in row.columns" :key="column.key">
          <InnerForm :item="column" :labelCol="labelCol"  :size="size" :curWidget="curWidget"
                     @maker-widget-change="handleWidgetChange"></InnerForm>
        </td>
      </tr>
    </table>
    <!-- 标签页布局 -->
    <a-tabs v-else-if="isType('tabs')"
            :activeKey="(widget.options && widget.options.items && widget.options.items.default[0]) || 0"
            :type="widget.options['tabType']"
            :tabPosition="widget.options.tabPosition"
            :size="size"
            :style="widget.options.style"
            @change="handleTabChange">
      <a-tab-pane v-for="item in widget.items"
                  :key="item.key"
                  :tab="item.title">
        <InnerForm :item="item" :labelCol="labelCol"  :size="size" :curWidget="curWidget"
                   @maker-widget-change="handleWidgetChange"></InnerForm>
      </a-tab-pane>
    </a-tabs>
    <!-- 分步布局 -->
    <div v-else-if="isType('steps')" :style="widget.options.style">
      <a-steps :type="widget.options['stepType']"
               :direction="widget.options.direction"
               :labelPlacement="widget.options.labelPlacement"
               :progressDot="widget.options.progressDot"
               :size="size === 'small' ? 'small' : 'default'"
               v-model:current="curStep"
               @change="handleStepChange">
        <a-step v-for="step in widget.items"
                :key="step.key"
                :title="step.title">
        </a-step>
      </a-steps>
      <div style="margin-top: 16px;">
        <InnerForm :item="widget.items[curStep]"
                   :labelCol="labelCol"
                   :size="size"
                   :curWidget="curWidget"
                   @maker-widget-change="handleWidgetChange"></InnerForm>
      </div>
    </div>
    <!-- 分隔线布局 -->
    <div v-else-if="isType('divider')" :style="widget.options.style">
      <a-divider :orientation="widget.options.orientation"
                 :plain="widget.options.plain"
                 :dashed="widget.options.dashed">{{ widget.options.title }}</a-divider>
    </div>
    <!-- 默认为文本布局 -->
    <div v-else :style="widget.options.style">
      <a-alert :type="widget.options['alertType']"
               :banner="widget.options.banner"
               :closable="widget.options.closable"
               :showIcon="widget.options.showIcon"
               :message="widget.options.message">
        <div class="atom-maker-actions" v-if="widget.key === curWidget.key">
          <a-tooltip title="复制">
            <a-button type="primary" size="small" @click.stop="handleWidgetCopy">
              <IconFont type="CopyOutlined"/>
            </a-button>
          </a-tooltip>
          <a-tooltip title="删除">
            <a-button type="primary" size="small" danger @click.stop="handleWidgetDelete">
              <IconFont type="DeleteOutlined"/>
            </a-button>
          </a-tooltip>
        </div>
      </a-alert>
    </div>
  </a-form-item>
</template>

<script>
/**
 * 布局构造器
 */
import FuncTitle from '@/components/Common/FuncTitle'
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
    // label的展示形式
    labelCol: {
      type: Object,
      required: false
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
            if ((target.type || target.__draggable_context.element.group) === 'layout') {
              message.warn({ key: 'message-warn', content: '无法在布局组件中加入布局组件！' })
            }
            // 在https://github.com/SortableJS/vue.draggable.next/pull/52文件进行修改合并后
            // 或者手工在vuedraggable.umd.js,vuedraggable.common.js,vuedraggable.js三个文件手工改动后，可以使用这个
            // 否则会在组件内部相互移动时报错
            return (target.type || target.__draggable_context.element.group) !== 'layout'
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
      this.$emit('maker-widget-change', item.widgets[index - 1] || item.widgets[index] || {})
    }
  },
  template: `
    <div class="atom-maker-inner-form">
      <Draggable v-bind="dragOptions"
                 :list="item.widgets"
                 itemKey="key"
                 tag="transition-group"
                 :component-data="{ name: 'fade' }"
                 @add="handleWidgetAdd($event, item)">
        <template #item="{ element, index }">
          <div :class="['atom-maker-item', element.key === curWidget.key ? 'active' : '']"
               @click.stop="handleWidgetChange(element)">
            <FormWidget :widget="element"
                        :labelCol="labelCol"
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
  components: { FuncTitle, InnerForm },
  props: {
    // 组件
    widget: {
      type: Object,
      required: true
    },
    // label的展示形式
    labelCol: {
      type: Object,
      required: false
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
      // 当前是第几步
      curStep: 0
    }
  },
  emits: ['maker-widget-change', 'maker-tab-change', 'maker-step-change', 'maker-widget-copy', 'maker-widget-delete'],
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.widget.type || 'text')
    },
    // 响应组件选择改变
    handleWidgetChange (curWidget) {
      this.$emit('maker-widget-change', curWidget)
    },
    // 响应tab切换
    handleTabChange (activeTab) {
      if (this.curWidget.key !== this.widget.key) {
        // 选中当前组件
        this.$emit('maker-widget-change', this.widget)
      }
      this.$emit('maker-tab-change', activeTab)
    },
    // 响应step切换
    handleStepChange () {
      if (this.curWidget.key !== this.widget.key) {
        // 选中当前组件
        this.$emit('maker-widget-change', this.widget)
      }
      // 根据curStep判断当前激活的step
      this.$emit('maker-step-change', this.widget.options['items'].options[this.curStep].value)
    },
    // 响应文本域的复制
    handleWidgetCopy () {
      this.$emit('maker-widget-copy')
    },
    // 响应文本域的删除
    handleWidgetDelete () {
      this.$emit('maker-widget-delete')
    }
  }
}
</script>
