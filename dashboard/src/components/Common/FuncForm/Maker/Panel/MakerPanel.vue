<template>
  <a-layout-content class="atom-maker-panel">
    <!-- 画布头部 -->
    <MakerHeader @maker-header-action="handleHeaderAction"
                 @maker-canvas-resize="handleCanvasResize"></MakerHeader>
    <div class="atom-maker-canvas-panel">
      <div :class="['atom-maker-canvas', panel]">
        <!-- 表单头 -->
        <FuncTitle v-if="formConfig.title"
                   :title="formConfig.title"
                   :style="{ width: `${formConfig.width}%` }"></FuncTitle>
        <!-- 表单区域 -->
        <a-form v-bind="formConfig" :style="{ width: `${formConfig.width}%` }">
          <!-- 有元素时 -->
          <Draggable v-bind="dragOptions"
                     v-model="widgets"
                     itemKey="key"
                     tag="transition-group"
                     :component-data="{ name: 'fade' }"
                     @add="handleWidgetAdd">
            <!-- FormItem渲染 -->
            <template #item="{ element, index }">
              <div :class="['atom-maker-item', element.group, element.key === curWidget.key ? 'active' : '']"
                   @click="handleWidgetChange(element)">
                <!-- 布局元素 -->
                <LayoutWidget v-if="element.group === 'layout'"
                              :widget="element"
                              :labelCol="formConfig.labelCol"
                              :size="formConfig.size"
                              :curWidget="curWidget"
                              @maker-widget-change="handleWidgetChange"
                              @maker-tab-change="handleTabChange($event, element)"
                              @maker-step-change="handleStepChange($event, element)"
                              @maker-widget-copy="handleWidgetCopy(element, index)"
                              @maker-widget-delete="handleWidgetDelete(element, index)"></LayoutWidget>
                <!-- form组件元素 -->
                <FormWidget v-else
                            :widget="element"
                            :labelCol="formConfig.labelCol"
                            :size="formConfig.size"></FormWidget>
                <!-- 当前选中组件时显示复制删除按钮 -->
                <div class="atom-maker-actions" v-if="element.key === curWidget.key">
                  <a-tooltip title="复制">
                    <a-button type="primary" size="small" @click.stop="handleWidgetCopy(element, index)"><IconFont type="CopyOutlined"/></a-button>
                  </a-tooltip>
                  <a-tooltip title="删除">
                    <a-button type="primary" size="small" danger @click.stop="handleWidgetDelete(element, index)"><IconFont type="DeleteOutlined"/></a-button>
                  </a-tooltip>
                </div>
              </div>
            </template>
          </Draggable>
          <!-- 没有元素时 -->
          <a-empty v-if="!widgets || widgets.length <= 0" description="从左侧拖拽或点击来添加字段"/>
        </a-form>
      </div>
    </div>
  </a-layout-content>
  <!-- 预览弹窗 -->
  <MakerPreview :visible="previewVisible"
               :title="formConfig.title"
               :formConfig="formConfig"
               :widgets="previewWidgets"
               @maker-preview-cancel="handlePreviewCancel"></MakerPreview>
</template>

<script>
/**
 * 画布面板
 */
import FuncTitle from '@/components/Common/FuncTitle'
import MakerHeader from '../Widget/MakerHeader'
import LayoutWidget from '../Widget/LayoutWidget'
import FormWidget from '../Widget/FormWidget'
import MakerPreview from '../Preview/MakerPreview'
import copy from '../mixins/copy'
export default {
  name: 'MakerPanel',
  components: { FuncTitle, MakerHeader, LayoutWidget, FormWidget, MakerPreview },
  mixins: [copy],
  props: {
    // 表单配置
    makerConfig: {
      type: Object,
      required: true
    },
    // 当前操作的组件
    curWidget: {
      type: Object,
      required: false
    }
  },
  data () {
    return {
      // 画布的样式 mac | pad | phone
      panel: 'mac',
      // 组件列表，从组件库中拖过来的信息，需要在结束后增加一个唯一性key
      widgets: [],
      // preview预览显隐
      previewVisible: false,
      // 拖动配置
      dragOptions: {
        animation: 300,
        group: { name: 'widgets', put: ['toolboxs', 'layouts'] },
        sort: true,
        ghostClass: 'atom-widget-ghost',
        // steps标题禁止拖动，防止与click事件重叠
        filter: '.ant-steps-item'
      }
    }
  },
  computed: {
    // 表单配置信息
    formConfig () {
      return {
        layout: (this.makerConfig.formConfig && this.makerConfig.formConfig.labelAlign === 'vertical')
            ? 'vertical' : 'horizontal',
        ...this.makerConfig.formConfig,
        labelAlign: (this.makerConfig.formConfig && this.makerConfig.formConfig.labelAlign === 'vertical')
            ? 'right' : (this.makerConfig.formConfig && this.makerConfig.formConfig.labelAlign)
      }
    },
    // 预览组件列表，深度复制一份，防止渲染时同一field在FieldRender组件中多次渲染引起的往复warn
    previewWidgets () {
      return this.$utils.deepClone(this.widgets)
    }
  },
  emits: ['maker-widget-change'],
  methods: {
    // 响应头部点击响应
    handleHeaderAction (action) {
      console.log(action)
      // 清空
      if (action.name === 'clear') {
        this.widgets = []
        this.$emit('maker-widget-change', {})
      } else if (action.name === 'preview') {
        // 预览
        this.previewVisible = true
      } else if (action.name === 'import') {
        // 导入
      } else if (action.name === 'export') {
        // 导出
      } else if (action.name === 'save') {
        // 保存
      }
    },
    // 响应画布大小调整
    handleCanvasResize (panel) {
      this.panel = panel
    },
    // 响应组件的增加
    handleWidgetAdd (event) {
      // 设置当前操作的组件
      this.$emit('maker-widget-change', this.widgets[event['newDraggableIndex']])
    },
    // 响应组件选择改变
    handleWidgetChange (widget) {
      // 设置当前操作的组件
      this.$emit('maker-widget-change', widget)
    },
    // 响应组件的复制操作
    handleWidgetCopy (widget, index) {
      this.$emit('maker-widget-change', this.widgetCopy(widget, index, this.widgets))
    },
    // 响应组件的删除操作
    handleWidgetDelete (widget, index) {
      this.widgets.splice(index, 1)
      this.$emit('maker-widget-change', this.widgets[index - 1] || this.widgets[index] || {})
    },
    // 响应tab切换
    handleTabChange (activeTab, widget) {
      widget.widgetConfig['tabs'].default = [activeTab]
    },
    // 响应step切换
    handleStepChange (activeStep, widget) {
      widget.widgetConfig['steps'].default = [activeStep]
    },
    // 响应表单预览取消
    handlePreviewCancel () {
      this.previewVisible = false
    }
  }
}
</script>
