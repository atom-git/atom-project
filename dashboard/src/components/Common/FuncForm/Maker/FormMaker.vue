<template>
  <a-layout class="atom-form-maker">
    <!-- 左侧组件面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-widget-panel" :width="280">
      <WidgetPanel></WidgetPanel>
    </a-layout-sider>
    <!-- 中间画布面板区域 -->
    <MakerPanel v-model="widgets"
                :undo="logState.undo"
                :redo="logState.redo"
                :makerConfig="makerConfig"
                :curWidget="curWidget"
                @maker-widget-change="handleWidgetChange"
                @maker-undo="handleUndo"
                @maker-redo="handleRedo"
                @maker-save="handleSave"></MakerPanel>
    <!-- 右侧配置面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-config-panel" :width="280">
      <ConfigPanel v-model="makerConfig"
                   :fields="configFields"
                   @form-config-change="handleFormConfigChange"
                   @widget-config-change="handleWidgetConfigChange"></ConfigPanel>
    </a-layout-sider>
  </a-layout>
</template>

<script>
/**
 * 动态表单制作
 */
import WidgetPanel from './Panel/WidgetPanel'
import MakerPanel from './Panel/MakerPanel'
import ConfigPanel from './Panel/ConfigPanel'
import config from '@/config/mixins/config'
import reconfig from './mixins/reconfig'
export default {
  name: 'FormMaker',
  components: { WidgetPanel, MakerPanel, ConfigPanel },
  mixins: [config, reconfig],
  props: {
    // 实现自定义表单双绑配置写入的参数
    modelValue: {
      type: Object,
      default: () => ({})
    }
  },
  data () {
    return {
      // 表单编辑器的配置
      makerConfig: {},
      // 组件列表
      widgets: [],
      // 当前操作的组件
      curWidget: {},
      // 编辑器日志
      makerLog: [],
      // 日志状态
      logState: {
        // 所在状态位置
        index: 0,
        // undo是否可用
        undo: false,
        // redo是否可用
        redo: false
      },
      // 涉及options的组件，此类组件不需要设置default属性
      optionsWidgets: ['select', 'radio', 'checkbox', 'cascader', 'treeSelect', 'transfer', 'tagCheck']
    }
  },
  computed: {
    // 配置字段
    configFields () {
      return this.curWidget.fields || []
    }
  },
  watch: {
    // 监听外部传的数据变化用于双绑
    modelValue: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.makerConfig = (newValue && newValue.makerConfig) || {}
        this.widgets = (newValue && newValue.widgets) || []
      }
    },
    // 监听配置变化，实现双绑
    makerConfig: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', { makerConfig: newValue, widgets: this.widgets })
        this.$emit('change', { makerConfig: newValue, widgets: this.widgets })
      }
    },
    // 监听组件变化，实现双绑
    widgets: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', { makerConfig: this.makerConfig, widgets: newValue })
        this.$emit('change', { makerConfig: this.makerConfig, widgets: newValue })
      }
    }
  },
  emits: ['maker-save'],
  methods: {
    // 响应撤销
    handleUndo () {
      // 记录日志索引位置
      this.logState.index--
      // 退到最后一步时，则不可再后退
      if (this.logState.index < 0) {
        this.logState.undo = false
        this.logState.index = 0
      }
      this.logState.redo = true
      // 重置配置信息
      const { makerConfig, widgets, curWidget } = this.$utils.deepClone(this.makerLog[this.logState.index])
      this.makerConfig = makerConfig
      this.widgets = widgets
      this.curWidget = curWidget
      console.log(this.makerConfig, this.widgets, this.curWidget)
    },
    // 响应重做
    handleRedo () {
      this.logState.index++
      // 用于判断重做是否可用
      if (this.makerLog.length > 0 && this.logState.index !== this.makerLog.length - 1) {
        this.logState.redo = false
      }
      console.log(this.makerConfig, this.widgets, this.curWidget)
    },
    // 推送日志
    pushLog () {
      // 如果日志所处位置不是最后一个，则把后面的动作日志清除
      if (this.makerLog.length > 0 && this.logState.index !== this.makerLog.length - 1) {
        this.makerLog.splice(this.logState.index + 1)
        this.logState.redo = false
      }
      // 加入日志
      this.makerLog.push(this.$utils.deepClone({
        makerConfig: this.makerConfig,
        widgets: this.widgets,
        curWidget: this.curWidget
      }))
      // 记录日志位置
      this.logState.index = this.makerLog.length - 1
      // 开启undo
      this.logState.undo = true
      console.log(this.makerConfig, this.widgets, this.curWidget)
    },
    // 响应当前填加的组件改变
    handleWidgetChange (curWidget) {
      this.curWidget = curWidget
      // 回写组件配置
      this.makerConfig.widgetConfig = curWidget.widgetConfig || {}
      // 加入变化日志
      this.pushLog()
    },
    // 响应form配置变化
    handleFormConfigChange () {
      // 加入变化日志
      this.pushLog()
    },
    // 响应组件的配置变化
    handleWidgetConfigChange (widgetConfig) {
      // 保存组件配置
      this.curWidget.widgetConfig = widgetConfig
      this.curWidget.options = {
        ...this.curWidget.options,
        name: this.curWidget.key,
        ...widgetConfig,
        style: { width: `${widgetConfig.width || 100}%` || '100%' }
      }
      // select时对配置参数进行格式化
      if (widgetConfig.options) {
        // 带选项配置的需要回写其选项配置结果
        if (this.optionsWidgets.includes(this.curWidget.type)) {
          this.curWidget.options.options = widgetConfig.options.options
          this.curWidget.options.default = widgetConfig.options.default
        }
      }
      // 组件无法直接挂载的参数调整
      this.widgetReconfig(this.curWidget, widgetConfig)
      // 加入变化日志
      this.pushLog()
    },
    // 响应保存提交
    handleSave () {
      this.$emit('maker-save')
    }
  }
}
</script>

<style lang="less">
@import './Style/formMaker';
</style>
