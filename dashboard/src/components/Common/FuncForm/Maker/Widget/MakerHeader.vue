<template>
  <a-row class="atom-maker-header">
    <a-col span="14" class="atom-maker-header-left">
      <!-- 画布切换 -->
      <TipButton v-for="panel in panelList"
                 :key="panel.name"
                 type="text"
                 :class="[this.activeKey === panel.name ? 'active' : '']"
                 :title="panel.title"
                 :icon="panel.icon"
                 placement="bottom"
                 @click="handleActivePanel(panel)"></TipButton>
      <a-divider type="vertical"/>
      <!-- 动作 undo | redo -->
      <TipButton title="撤销"
                 type="text"
                 :disabled="!active.undo"
                 icon="atom-undo"
                 placement="bottom"
                 @click="handleAction({ name: 'undo' })"></TipButton>
      <TipButton title="重做"
                 type="text"
                 :disabled="!active.redo"
                 icon="atom-redo"
                 placement="bottom"
                 @click="handleAction({ name: 'redo' })"></TipButton>
    </a-col>
    <a-col span="10" class="atom-maker-header-right">
      <!-- 导入配置 | 清空 | 预览内部分为界面预览和JSON代码预览 -->
      <TipButtonGroup type="both" :actions="actionList" :divider="false" @click="handleAction"></TipButtonGroup>
    </a-col>
  </a-row>
</template>

<script>
/**
 * 画板头部组件
 */
import { TipButton, TipButtonGroup } from '@/components/Common/FuncButton'
import config from '@/config/mixins/config'
export default {
  name: 'MakerHeader',
  components: { TipButtonGroup, TipButton },
  props: {
    // 当前操作是否有undo | redo由canvas判断后交给header来判断其状态
    active: {
      type: Object,
      default: () => ({ undo: false, redo: false })
    }
  },
  mixins: [config],
  data () {
    return {
      // 画板类型
      panelList: [
        { icon: 'atom-mac', title: '电脑', name: 'mac' },
        { icon: 'atom-pad', title: '平板', name: 'pad' },
        { icon: 'atom-phone', title: '手机', name: 'phone' }
      ],
      // 当前激活的画板
      activeKey: 'mac',
      // 常用操作
      actionList: [
        { icon: 'ClearOutlined', title: '清空', name: 'clear' },
        { icon: 'EyeOutlined', title: '预览', name: 'preview' },
        { icon: 'SettingOutlined', title: '操作',
          children: [
            { icon: 'ImportOutlined', title: '导入', name: 'import' },
            { icon: 'ExportOutlined', title: '导出', name: 'export' },
            { icon: 'SaveOutlined', title: '保存', name: 'save' }
          ]
        }
      ]
    }
  },
  emits: ['maker-header-action', 'maker-canvas-resize'],
  methods: {
    // 响应画板模式切换
    handleActivePanel (panel) {
      this.activeKey = panel.name
      this.$emit('maker-canvas-resize', this.activeKey)
    },
    // 响应功能点击
    handleAction (action) {
      this.$emit('maker-header-action', action)
    }
  }
}
</script>
