<template>
  <a-collapse v-model:activeKey="activeKey" :bordered="false">
    <a-collapse-panel v-for="widget in widgets"
                      :key="widget.key"
                      :showArrow="false">
      <template #header>
        <FuncTitle :title="`${widget.title}[${widget.items.length}]`"></FuncTitle>
      </template>
      <Draggable class="atom-widget-list"
                 tag="ul"
                 :list="widget.items"
                 itemKey="type"
                 v-bind="dragOptions">
        <template #item="{ element }">
          <li class="atom-widget">
            <IconFont :type="element.icon" />{{ element.title }}
          </li>
        </template>
      </Draggable>
    </a-collapse-panel>
  </a-collapse>
</template>

<script>
/**
 * 组件面板
 */
import Draggable from 'vuedraggable'
import FuncTitle from '@/components/Common/FuncTitle'
export default {
  name: 'WidgetPanel',
  components: { Draggable, FuncTitle },
  props: {},
  data () {
    return {
      // 当前激活的组件库
      activeKey: ['layout', 'basic', 'advance'],
      // 组件库
      widgets: [layoutWidgets, basicWidgets, advanceWidgets],
      // 拖动配置
      dragOptions: {
        animation: 0,
        group: { name: 'widgets', pull: 'clone', put: false },
        sort: false,
        ghostClass: 'atom-widget-ghost'
      }
    }
  }
}
/**
 * 布局组件
 */
const layoutWidgets = {
  key: 'layout',
  title: '布局组件',
  items: [
    { icon: 'atom-layout-grid', title: '栅格布局', type: 'grid' },
    { icon: 'atom-layout-table', title: '表格布局', type: 'table' },
    { icon: 'atom-layout-tab', title: '标签布局', type: 'tab' },
    { icon: 'atom-layout-space', title: '行列布局', type: 'space' },
    { icon: 'atom-layout-step', title: '分步布局', type: 'step' },
    { icon: 'atom-layout-desc', title: '描述布局', type: 'desc' },
    { icon: 'atom-layout-divider', title: '分割线', type: 'divider' }
  ]
}
/**
 * 基础组件
 */
const basicWidgets = {
  key: 'basic',
  title: '基础组件',
  items: [
    { icon: 'atom-form-input', title: '单行文本', type: 'input' },
    { icon: 'atom-form-textarea', title: '多行文本', type: 'textarea' },
    { icon: 'atom-form-number', title: '计数器', type: 'number' },
    { icon: 'atom-form-select', title: '选择器', type: 'select' },
    { icon: 'atom-form-radio', title: '单选框组', type: 'radio' },
    { icon: 'atom-form-checkbox', title: '多选框组', type: 'checkbox' },
    { icon: 'atom-form-switch', title: '开关', type: 'switch' },
    { icon: 'atom-form-cascader', title: '级联选择', type: 'cascader' },
    { icon: 'atom-form-datePicker', title: '日期选择器', type: 'datePicker' },
    { icon: 'atom-form-monthPicker', title: '月份选择器', type: 'monthPicker' },
    { icon: 'atom-form-rangePicker', title: '区间选择器', type: 'rangePicker' },
    { icon: 'atom-form-weekPicker', title: '周选择器', type: 'weekPicker' },
    { icon: 'atom-form-timePicker', title: '时间选择器', type: 'timePicker' },
    { icon: 'atom-form-title', title: '标题', type: 'title' },
    { icon: 'atom-form-text', title: '文本', type: 'text' },
    { icon: 'atom-form-button', title: '按钮', type: 'button' },
    { icon: 'atom-form-link', title: '链接', type: 'link' },
    { icon: 'atom-form-html', title: 'HTML', type: 'html' }
  ]
}
/**
 * 高阶组件
 */
const advanceWidgets = {
  key: 'advance',
  title: '高阶组件',
  items: [
    { icon: 'atom-form-treeSelect', title: '树选择器', type: 'treeSelect' },
    { icon: 'atom-form-transfer', title: '穿梭框', type: 'transfer' },
    { icon: 'atom-form-slider', title: '滑块', type: 'slider' },
    { icon: 'atom-form-mentions', title: '提及选择', type: 'mentions' },
    { icon: 'atom-form-rate', title: '评分', type: 'rate' },
    { icon: 'atom-form-inputGroup', title: '组合组件', type: 'inputGroup' },
    { icon: 'atom-form-fileUpload', title: '文件', type: 'fileUpload' },
    { icon: 'atom-form-imagePicker', title: '图片', type: 'imagePicker' },
    { icon: 'atom-form-iconPicker', title: '图标', type: 'iconPicker' },
    { icon: 'atom-form-tagCheck', title: '标签多选', type: 'tagCheck' },
    { icon: 'atom-form-mapPicker', title: '地图选择', type: 'mapPicker' },
    { icon: 'atom-form-tableSelect', title: '列表选择', type: 'tableSelect' },
    { icon: 'atom-form-richText', title: '富文本', type: 'richText' },
  ]
}
</script>
