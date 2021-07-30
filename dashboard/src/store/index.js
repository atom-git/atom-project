/**
 * vuex缓存插件
 */
import { createStore, createLogger } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import Default from '@/config/default'

/**
 * app 应用配置相关
 * permission 系统权限相关
 * user 系统用户相关
 */
import modules from './modules'
/**
 * getters数据获取方式
 */
import getters from './getters'

/**
 * 引入的插件
 * createLogger: 日志记录
 * createPersistedState: 持久化记录
 */
function bindPlugins () {
  const plugins = []
  /**
   * 是否debug模式
   */
  const debug = process.env.NODE_ENV !== 'production'
  if (debug) {
    plugins.push(
      createLogger({
        // 自动展开记录的 mutation
        collapsed: false,
        // 记录 action 日志
        logActions: false,
        // 记录 mutation 日志
        logMutations: false,
        // 自定义 console 实现，默认为 `console`
        logger: console
      })
    )
  }
  /**
   * 增加持久化插件
   */
  plugins.push(
    createPersistedState({
      key: Default.storeOptions.key,
      storage: Default.storeOptions.storage
      // 保留全部的原因是插件会将值直接写入state而不是写入到moduals中
      // reducer (state) {
      //   return {
      //     primaryColor: state.app.primaryColor,
      //     theme: state.app.theme,
      //     layout: state.app.layout,
      //     multiTab: state.app.multiTab,
      //     multiTabDraggable: state.app.multiTabDraggable,
      //     fixHeader: state.app.fixHeader,
      //     transition: state.app.transition,
      //     token: state.user.token,
      //     userInfo: state.user.userInfo,
      //     openRoutes: state.user.openRoutes,
      //     asyncRoute: state.permission.asyncRoute,
      //     menus: state.permission.menus,
      //     actions: state.permission.actions,
      //     generated: state.permission.generated
      //   }
      // }
    })
  )
  return plugins
}

/**
 * 创建store
 * setUpStore: 注入到vue对象
 */
export const store = createStore({
  modules,
  getters,
  plugins: bindPlugins()
})

/**
 * store装配函数
 * @param app app实例
 */
export function setUpStore (app) {
  app.use(store)
}
