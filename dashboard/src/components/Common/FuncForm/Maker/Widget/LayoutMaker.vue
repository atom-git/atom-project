<template>
  <!-- 栅格布局 -->
  <a-row v-if="isType('grid')"
         :align="widget.options.align"
         :gutter="[widget.options.gutter, 24]"
         :justify="widget.options.justify"
         :wrap="widget.options.wrap"
         :style="widget.options.style">
    <!-- 有元素时 -->
    <Draggable :list="widgets"
               v-bind="dragOptions"
               itemKey="key"
               tag="transition-group"
               :component-data="{ name:'fade' }"
               @add="handleWidgetAdd">
      <!-- FormItem渲染 -->
      <template #item="{ element }">
        <a-col v-for="col in widget.options.cols"
               :key="col.order"
               :span="col.span"
               :order="col.order">
          <a-form-item :key="element.key"
                       :class="[element.key === curWidget.key ? 'active' : '']"
                       :name="element.key"
                       :label="element.group === 'layout' || !element.options.labelVisible ? '' : element.options.label"
                       @click="handleWidgetChange(element)">
            <!-- 布局元素 -->
            <LayoutMaker v-if="element.group === 'layout'" :widget="element"></LayoutMaker>
            <!-- form组件元素 -->
            <FieldRender v-else
                         :field="element.options"
                         :modelValue="element.options.default"
                         :size="formConfig.size"></FieldRender>
          </a-form-item>
        </a-col>
      </template>
    </Draggable>
  </a-row>
  <!-- 标题布局 -->
  <FuncTitle v-if="isType('title')" :title="widget.options.default"></FuncTitle>
  <!-- 文本链接 -->
  <a v-else-if="isType('link')"
     :href="widget.options.href || ''"
     target="_blank">{{ widget.options.content || '外部链接' }}</a>
  <!-- html -->
  <div v-else-if="isType('html')"
       v-html="widget.options.content || 'html内容'"></div>
  <!-- 默认为文本布局 -->
  <div v-else>{{ widget.options.default }}</div>
</template>

<script>
/**
 * 布局构造器
 */
import FuncTitle from '@/components/Common/FuncTitle'
export default {
  name: 'LayoutMaker',
  components: { FuncTitle },
  props: {
    // 组件
    widget: {
      type: Object,
      required: true
    }
  },
  // 禁用继承
  inheritAttrs: false,
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.widget.type || 'text')
    }
  }
}
</script>
