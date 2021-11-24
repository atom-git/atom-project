<template>
  <a-layout class="atom-form-maker">
    <!-- 左侧组件面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-widget-panel" :width="280">
      <WidgetPanel></WidgetPanel>
    </a-layout-sider>
    <!-- 中间画布面板区域 -->
    <MakerPanel v-model="widgets"
                :panel="panel"
                :undo="logState.undo"
                :redo="logState.redo"
                :formConfig="formConfig"
                :curWidget="curWidget"
                @maker-panel-change="handlePanelChange"
                @maker-widget-change="handleWidgetChange"
                @maker-undo="handleUndo"
                @maker-redo="handleRedo"
                @maker-save="handleSave"></MakerPanel>
    <!-- 右侧配置面板区域 -->
    <a-layout-sider :theme="contentTheme" class="atom-config-panel" :width="280">
      <ConfigPanel ref="configPanel"
                   v-model:formConfig="formConfig"
                   :widgetConfig="curWidget.widgetConfig"
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
    formMaker: {
      type: Object,
      default: () => ({})
    },
    // 画布类型 mac | pad | phone
    panel: {
      type: String,
      default: 'mac'
    }
  },
  data () {
    return {
      // 自定义表单配置
      formConfig: {},
      // 组件列表
      widgets: [],
      // 当前操作的组件
      curWidget: {},
      // 编辑器历史
      makerLog: [],
      // 历史状态
      logState: {
        // 历史状态是否准备好
        ready: true,
        /**
         * 组件操作状态
         * 默认wait
         * add copy delete selete config由widget config变化触发日志
         * undo redo不触发日志，仅在日志链中移动
         */
        widget: 'wait',
        // 所在历史状态位置，0的位置为初始状态，初时状态时，undo不开启
        index: 0,
        // undo是否可用
        undo: false,
        // redo是否可用
        redo: false
      },
      // 涉及options的组件，此类组件不需要设置default属性
      singleOptions: ['select', 'radio'],
      multiOptions: ['checkbox', 'cascader', 'treeSelect', 'transfer', 'tagCheck']
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
    formMaker: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.formConfig = newValue.formConfig || {}
        this.widgets = newValue.widgets || []
      }
    },
    // 监听内部的值变化
    formConfig: {
      deep: true,
      handler (newValue) {
        this.$emit('update:formMaker', { formConfig: newValue, widgets: this.widgets })
      }
    },
    // 监听内部的值变化
    widgets: {
      deep: true,
      handler (newValue) {
        this.$emit('update:formMaker', { formConfig: this.formConfig, widgets: newValue })
      }
    }
  },
  emits: ['maker-save', 'update:formMaker', 'maker-panel-change'],
  methods: {
    // 响应撤销
    handleUndo () {
      // 记录一次动作导致的属性变化，当次不触发历史的二次记录
      this.logState.ready = false
      // 移动历史位置
      this.logState.index--
      // 退到初始状态时，则不可再后退
      if (this.logState.index < 1) {
        this.logState.undo = false
        this.logState.index = 0
      }
      this.logState.redo = true
      this.reconfigMaker('undo')
    },
    // 响应重做
    handleRedo () {
      // 记录一次动作导致的属性变化，当次不触发历史的二次记录
      this.logState.ready = false
      // 移动历史位置
      this.logState.index++
      // 用于判断重做是否可用
      if (this.makerLog.length > 0 && this.logState.index === this.makerLog.length - 1) {
        this.logState.redo = false
      }
      this.logState.undo = true
      this.reconfigMaker('redo')
    },
    // 重置maker配置信息
    reconfigMaker (action) {
      // 重置配置信息
      const { formConfig, widgets, curWidget } = this.$utils.deepClone(this.makerLog[this.logState.index])
      this.formConfig = formConfig
      this.widgets = widgets
      this.curWidget = curWidget
      this.logState.widget = action
    },
    // 推送历史
    pushLog (state) {
      // 如果有历史移动，或者是组件变化的初始化动作，则初次不记录历史
      if (this.logState.ready) {
        // 如果历史所处位置不是最后一个，则把后面的动作历史清除
        if (this.makerLog.length > 0 && this.logState.index !== this.makerLog.length - 1) {
          this.makerLog.splice(this.logState.index + 1)
          this.logState.redo = false
        }
        // 加入历史
        this.makerLog.push(this.$utils.deepClone({
          formConfig: this.formConfig,
          widgets: this.widgets,
          curWidget: this.curWidget
        }))
        // 记录历史位置
        this.logState.index = this.makerLog.length - 1
        // 开启undo，初始状态时不开启undo
        if (this.makerLog.length > 1) {
          this.logState.undo = true
        }
        // 日志记录完成后widget状态变为wait状态
        console.log(state, this.makerLog, this.logState)
      } else {
        this.logState.ready = true
      }
    },
    // 响应画布切换
    handlePanelChange (panel) {
      this.$emit('maker-panel-change', panel)
    },
    // 响应当前填加的组件改变
    handleWidgetChange (curWidget, action = 'select') {
      // 切换tab页
      this.$refs.configPanel.toggleToConfig('field')
      this.curWidget = curWidget
      // 设置操作状态，在widget config发生改变时再做日志记录
      this.logState.widget = action
    },
    // 响应form配置变化
    handleFormConfigChange () {
      // 加入变化历史
      this.pushLog('form config change')
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
        if (this.singleOptions.includes(this.curWidget.type)) {
          this.curWidget.options.options = widgetConfig.options.options
          this.curWidget.options.default = widgetConfig.options.default[0]
        } else if (this.multiOptions.includes(this.curWidget.type)) {
          this.curWidget.options.options = widgetConfig.options.options
          this.curWidget.options.default = widgetConfig.options.default
        }
      }
      // 组件无法直接挂载的参数调整
      this.widgetReconfig(this.curWidget, widgetConfig)
      /**
       * add copy delete select事件发生时，由于widget的config还未渲染，因此首轮仅修改状态为config状态，此类事件不作历史记录
       * wait状态，发生config改变时，触发为config状态，且作日志记录
       * 其他状态改变为config状态下才组织日志的记录
        */
      if (['add', 'copy', 'delete', 'select'].includes(this.logState.widget)) {
        this.logState.widget = 'config'
        console.log(this.logState.widget)
      } else if (this.logState.widget === 'wait') {
        this.logState.widget = 'config'
        this.pushLog('widget wait change')
      } else if (this.logState.widget === 'config') {
        this.pushLog('widget config change')
      }
    },
    // 响应保存提交
    handleSave () {
      this.$refs.configPanel.triggerFormConfigValidate().then(() => {
        this.$emit('maker-save', this.formConfig, this.widgets)
      }).catch(() => {
        this.$refs.configPanel.toggleToConfig('form')
        this.$message.error('表单标题必须填写')
      })
    }
  }
}
</script>

<style lang="less">
@import './Style/formMaker';
</style>
