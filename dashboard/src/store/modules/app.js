import Default from '@/config/default'
import device from '@/config/lib/device'
/**
 * 应用配置相关信息
 * title: 项目名称
 * primaryColor: 主题色
 * theme: 主题
 * layout: 整体布局方式
 * multiTab: 多标签页是否开启
 * multiTabDraggable: 多标签页是否可拖拽
 * fixHeader: 固定顶部
 * transition: 路由切换动画
 * device: 设备信息，根据userAgent信息自动获取
 * scrollTop: 滚动条上部偏移量
 * clientWidth: 窗体的宽度
 * clientHeight: 窗体的高度
 * contentHeight: 内容的高度
 * loading: 路由是否加载中
 * waterMarkEnable: 水印是否打开
 */
const app = {
  state: {
    title: Default.title,
    primaryColor: Default.primaryColor,
    theme: Default.theme,
    layout: device().isMobile || (document.body.clientWidth || window.innerWidth) < 768 ? Default.mobileLayout : Default.layout,
    multiTab: Default.multiTab,
    multiTabDraggable: Default.multiTabDraggable,
    fixHeader: Default.fixHeader,
    transition: Default.transition,
    device: device(),
    scrollTop: 0,
    clientWidth: document.body.clientWidth || window.innerWidth,
    clientHeight: document.body.clientHeight || window.innerHeight,
    contentHeight: (document.body.clientHeight || window.innerHeight) - 48 - 68 - (8 * 2 + 16),
    loading: false,
    waterMarkEnable: Default.waterMarkEnable
  },
  mutations: {
    setPrimaryColor: (state, primaryColor) => {
      state.primaryColor = primaryColor
    },
    setTheme: (state, theme) => {
      state.theme = theme
    },
    setLayout: (state, layout) => {
      state.layout = state.device.isMobile || (document.body.clientWidth || window.innerWidth) < 768 ? Default.mobileLayout : layout
      // 如果是mix布局，fixHeader一定为true，并且不可更改，drawer默认为fixed，但是可以改变
      if (layout === 'mix' || layout === 'drawer') {
        state.fixHeader = true
      }
    },
    setMultiTab: (state, multiTab) => {
      state.multiTab = multiTab
    },
    setMultiTabDraggable: (state, multiTabDraggable) => {
      state.multiTabDraggable = multiTabDraggable
    },
    setFixHeader: (state, fixHeader) => {
      state.fixHeader = fixHeader
    },
    setTransition: (state, transition) => {
      state.transition = transition
    },
    setScrollTop: (state, scrollTop) => {
      state.scrollTop = scrollTop
    },
    setClientWidth: (state, clientWidth) => {
      state.clientWidth = clientWidth
    },
    setClientHeight: (state, clientHeight) => {
      state.clientHeight = clientHeight
      // 48:header高度 68:footer高度 (8*2 + 16):content上下边距，footer上边距
      state.contentHeight = clientHeight - 48 - 68 - (8 * 2 + 16)
    },
    setLoading: (state, loading) => {
      state.loading = loading
    },
    setWaterMarkEnable: (state, waterMarkEnable) => {
      state.waterMarkEnable = waterMarkEnable
    }
  },
  actions: {
    setPrimaryColor ({ commit }, primaryColor) {
      commit('setPrimaryColor', primaryColor)
    },
    setTheme ({ commit }, theme) {
      commit('setTheme', theme)
    },
    setLayout ({ commit }, layout) {
      commit('setLayout', layout)
    },
    setMultiTab ({ commit }, multiTab) {
      commit('setMultiTab', multiTab)
    },
    setMultiTabDraggable ({ commit }, multiTabDraggable) {
      commit('setMultiTabDraggable', multiTabDraggable)
    },
    setFixHeader ({ commit }, fixHeader) {
      commit('setFixHeader', fixHeader)
    },
    setTransition ({ commit }, transition) {
      commit('setTransition', transition)
    },
    setScrollTop ({ commit }, scrollTop) {
      commit('setScrollTop', scrollTop)
    },
    setClientWidth ({ commit }, clientWidth) {
      commit('setClientWidth', clientWidth)
    },
    setClientHeight ({ commit }, clientHeight) {
      commit('setClientHeight', clientHeight)
    },
    setLoading ({ commit }, loading) {
      commit('setLoading', loading)
    },
    setWaterMarkEnable ({ commit }, waterMarkEnable) {
      commit('setWaterMarkEnable', waterMarkEnable)
    }
  }
}

export default app

