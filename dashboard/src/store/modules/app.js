import Default from '@/config/default'
import device from '@/config/lib/device'
import Utils from '@/utils'
import { toggleTheme } from '@/config/theme'
/**
 * 应用配置相关信息
 * title: 项目名称
 * config: App应用配置
 * theme: 主题
 * primaryColor: 主题色
 * layout: 整体布局方式
 * fixHeader: 固定顶部
 * multiTab: 多标签页是否开启
 * multiTabDraggable: 多标签页是否可拖拽
 * dialog: 弹窗样式
 * waterMark: 水印是否打开
 * transition: 路由切换动画
 * device: 设备信息，根据userAgent信息自动获取
 * scrollTop: 滚动条上部偏移量
 * clientWidth: 窗体的宽度
 * clientHeight: 窗体的高度
 * contentHeight: 内容的高度
 * loading: 路由是否加载中
 */
const app = {
  state: {
    title: Default.title,
    config: {
      theme: Default.theme,
      primaryColor: Default.primaryColor,
      layout: device().isMobile || (document.body.clientWidth || window.innerWidth) < 768 ? Default.mobileLayout : Default.layout,
      fixHeader: Default.fixHeader,
      multiTab: Default.multiTab,
      multiTabDraggable: Default.multiTabDraggable,
      dialog: Default.dialog,
      waterMark: Default.waterMark,
      transition: Default.transition
    },
    device: device(),
    scrollTop: 0,
    clientWidth: document.body.clientWidth || window.innerWidth,
    clientHeight: document.body.clientHeight || window.innerHeight,
    contentHeight: document.body.clientHeight || window.innerHeight,
    loading: false
  },
  mutations: {
    setConfig: (state, config) => {
      state.config = config
    },
    setTheme: (state, theme) => {
      state.config.theme = theme
    },
    setPrimaryColor: (state, primaryColor) => {
      state.config.primaryColor = primaryColor
    },
    setLayout: (state, layout) => {
      state.config.layout = state.device.isMobile || (document.body.clientWidth || window.innerWidth) < 768 ? Default.mobileLayout : layout
      // 如果是mix布局，fixHeader一定为true，并且不可更改，drawer默认为fixed，但是可以改变
      if (layout === 'mix') {
        state.config.fixHeader = true
      } else if (layout === 'drawer') {
        state.config.fixHeader = true
        state.config.multiTab = false
        state.config.multiTabDraggable = false
      }
      // drawer模式即手机端模式时，弹窗均为drawer且，最大宽度为100%
      if (layout === 'drawer') {
        state.config.dialog.type = 'drawer'
        state.config.dialog.size = '100%'
      } else {
        state.config.dialog = Default.dialog
      }
    },
    setFixHeader: (state, fixHeader) => {
      state.config.fixHeader = fixHeader
    },
    setMultiTab: (state, multiTab) => {
      state.config.multiTab = multiTab
    },
    setMultiTabDraggable: (state, multiTabDraggable) => {
      state.config.multiTabDraggable = multiTabDraggable
    },
    setDialog: (state, dialog) => {
      state.config.dialog = dialog
    },
    setWaterMark: (state, waterMark) => {
      state.config.waterMark = waterMark
    },
    setTransition: (state, transition) => {
      state.config.transition = transition
    },
    setScrollTop: (state, scrollTop) => {
      state.scrollTop = scrollTop
    },
    setClientWidth: (state, clientWidth) => {
      state.clientWidth = clientWidth
    },
    setClientHeight: (state, clientHeight) => {
      state.clientHeight = clientHeight
      // 修改内容部分高度
      // 48:header高度 68:footer高度 footer边距(边距8+8)content上下边距(16+16)multiTabs高度44
      state.contentHeight = clientHeight - 48 - 68 - (8 + 8) - (16 + 16) - (state.config.multiTab ? 44 : 0)
    },
    setLoading: (state, loading) => {
      state.loading = loading
    }
  },
  actions: {
    setConfig ({ commit }, config) {
      return new Promise(resolve => {
        if (config) {
          let appConfig
          if (Utils.isObject(config)) {
            appConfig = config
          } else {
            appConfig = JSON.parse(config)
          }
          if (appConfig) {
            commit('setConfig', appConfig)
            toggleTheme(appConfig.theme, appConfig.primaryColor).then(() => {
              console.log('主题已切换')
              resolve()
            })
          } else {
            resolve()
          }
        } else {
          console.log(this.state.config)
          resolve()
        }
      })
    },
    setTheme ({ commit }, theme) {
      commit('setTheme', theme)
    },
    setPrimaryColor ({ commit }, primaryColor) {
      commit('setPrimaryColor', primaryColor)
    },
    setLayout ({ commit }, layout) {
      commit('setLayout', layout)
    },
    setFixHeader ({ commit }, fixHeader) {
      commit('setFixHeader', fixHeader)
    },
    setMultiTab ({ commit }, multiTab) {
      commit('setMultiTab', multiTab)
    },
    setMultiTabDraggable ({ commit }, multiTabDraggable) {
      commit('setMultiTabDraggable', multiTabDraggable)
    },
    setDialog ({ commit }, dialog) {
      commit('setDialog', dialog)
    },
    setWaterMark ({ commit }, waterMark) {
      commit('setWaterMark', waterMark)
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
    }
  }
}

export default app

