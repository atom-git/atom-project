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
        <FormWidget v-for="element in column.widgets"
                    :key="element.key"
                    :widget="element"
                    :labelCol="labelCol"
                    :size="size"></FormWidget>
      </a-col>
    </a-row>
    <!-- 表格布局 -->
    <table v-else-if="isType('table')" :style="widget.options.style">
      <tr v-for="row in widget.items" :key="row.key">
        <td v-for="(column, index) in row.columns" :key="column.key" :style="{ paddingLeft: index !== 0 ? '6px' : 0 }">
          <FormWidget v-for="element in column.widgets"
                      :key="element.key"
                      :widget="element"
                      :labelCol="labelCol"
                      :size="size"></FormWidget>
        </td>
      </tr>
    </table>
    <!-- 标签页布局 -->
    <a-tabs v-else-if="isType('tabs')"
            v-model:activeKey="current"
            :type="widget.options['tabType']"
            :tabPosition="widget.options.tabPosition"
            :size="size"
            :style="widget.options.style">
      <a-tab-pane v-for="(item, index) in widget.items"
                  :key="index"
                  :tab="item.title">
        <FormWidget v-for="element in item.widgets"
                    :key="element.key"
                    :widget="element"
                    :labelCol="labelCol"
                    :size="size"></FormWidget>
      </a-tab-pane>
    </a-tabs>
    <!-- 分步布局 -->
    <div v-else-if="isType('steps')" :style="widget.options.style">
      <a-steps :type="widget.options['stepType']"
               :direction="widget.options.direction"
               :labelPlacement="widget.options.labelPlacement"
               :progressDot="widget.options.progressDot"
               :size="size === 'small' ? 'small' : 'default'"
               v-model:current="current">
        <a-step v-for="step in widget.items"
                :key="step.key"
                :title="step.title">
        </a-step>
      </a-steps>
      <div style="margin-top: 16px;">
        <FormWidget v-for="element in widget.items[current].widgets"
                    :key="element.key"
                    :widget="element"
                    :labelCol="labelCol"
                    :size="size"></FormWidget>
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
      </a-alert>
    </div>
  </a-form-item>
</template>

<script>
/**
 * 布局组件预览
 */
import FuncTitle from '@/components/Common/FuncTitle'
import FormWidget from '../Widget/FormWidget'
export default {
  name: 'LayoutPreview',
  components: { FuncTitle, FormWidget },
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
    }
  },
  data () {
    return {
      // 当前是第几步
      current: 0
    }
  },
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.widget.type || 'text')
    }
  }
}
</script>
