<template>
  <a-dropdown :trigger="['click']"
              placement="bottomCenter"
              v-model:visible="dropdown"
              overlayClassName="atom-icon-picker"
              :getPopupContainer="getPopupContainer">
    <a-input :value="selectedIcon" :size="size" allowClear>
      <template #prefix>
        <IconFont :type="selectedIcon || 'QuestionCircleOutlined'"/>
      </template>
    </a-input>
    <template #overlay>
      <a-card :style="{ width: dropdownWidth }">
        <a-tabs v-model:activeKey="activeTab">
          <a-tab-pane v-for="tab in tabList" :key="tab.key" :tab="tab.tab" class="atom-icon-picker-container">
            <span v-for="(icon, index) in icons[tab.key]"
                  :key="index"
                  :class="['atom-icon-picker-item', selectedIcon === icon ? 'selected' : '']"
                  @click="handleIconSelect(icon)">
              <IconFont :type="icon"/>
            </span>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </template>
  </a-dropdown>
</template>

<script>
// 图标选择器
import icons from './icons'
export default {
  name: 'IconPicker',
  props: {
    // 双绑的值
    modelValue: {
      type: String,
      required: false
    },
    // 组件大小 large|default|small
    size: {
      type: String,
      default: 'default'
    }
  },
  data () {
    return {
      // 是否dropdown
      dropdown: false,
      // 下拉菜单的宽度
      popupContainer: null,
      // icon选择类型面板
      tabList: [
        { key: 'direction', tab: '方向图标' },
        { key: 'advice', tab: '提示图标' },
        { key: 'form', tab: '编辑图标' },
        { key: 'data', tab: '数据图标' },
        { key: 'web', tab: '网站图标' },
        { key: 'brand', tab: '品牌图标' },
        { key: 'iconfont', tab: 'IconFont' }
      ],
      // 当前活动的tab
      activeTab: 'direction',
      // 图标集
      icons,
      // 选中的图标，默认情况展示
      selectedIcon: 'QuestionCircleOutlined',
      // 计算下拉菜单宽度
      dropdownWidth: '100%'
    }
  },
  watch: {
    // 监听外部的值变化
    modelValue (newValue) {
      this.selectedIcon = newValue
    }
  },
  mounted () {
    // 响应窗体宽度变化
    const self = this
    window.addEventListener('resize', () => {
      if (self.popupContainer) {
        self.dropdownWidth = self.popupContainer.clientWidth + 'px'
      }
    })
  },
  emits: ['update:modelValue', 'change'],
  methods: {
    // 获取挂载点的位置
    getPopupContainer (triggerNode) {
      this.popupContainer = triggerNode
      this.dropdownWidth = this.popupContainer.clientWidth + 'px'
      return triggerNode.parentNode
    },
    // 响应图标的选中
    handleIconSelect (icon) {
      this.selectedIcon = icon
      this.$emit('update:modelValue', icon)
      this.$emit('change', icon)
      this.dropdown = false
    }
  }
}
</script>

<style lang="less">
@import "iconPicker";
</style>
