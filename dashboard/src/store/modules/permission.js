import Utils from '@/utils'
import { asyncRoutes } from '@/router/modules'
import { router } from '@/router'
/**
 * 系统权限相关
 * asyncRoute: 异步路由
 * menus: 系统菜单权限
 * actions: 系统操作权限
 * generated: 权限是否已生成
 */
const permission = {
  state: {
    asyncRoute: [],
    menus: [],
    actions: [],
    generated: false
  },
  mutations: {
    // 设置异步路由
    setAsyncRoute: (state, asyncRoute) => {
      state.asyncRoute = asyncRoute
    },
    // 设置系统菜单menus
    setMenus: (state, menus) => {
      state.menus = menus
    },
    // 设置系统访问权限actions
    setActions: (state, actions) => {
      state.actions = actions
    },
    // 设置权限是否生成的标记位
    setGenerated: (state, generated) => {
      state.generated = generated
    },
    // 清除权限
    clearPermission: (state) => {
      // 清除异步路由，父级路由清理完，子路由也消失
      state.asyncRoute.forEach(route => {
        router.removeRoute(route.name)
      })
      state.menus = []
      state.actions = []
      state.asyncRoute = []
      state.generated = false
    }
  },
  actions: {
    // 生成系统权限
    generatePermission ({ commit }, { menus, actions }) {
      return new Promise(resolve => {
        if (Utils.isValid(menus)) {
          commit('setMenus', menus)
          commit('setActions', actions)
          // 平铺菜单
          const menuMap = {}
          Utils.tileTree(menuMap, menus, 'route')
          // 根据菜单树构建动态路由
          const asyncRoute = filterAsyncRoute(asyncRoutes, menuMap)
          asyncRoute.forEach(route => {
            router.addRoute(route)
          })
          commit('setAsyncRoute', asyncRoute)
          // 设置权限生成标志位
          commit('setGenerated', true)
          router.isReady().then(() => {
            resolve(asyncRoute)
          })
        } else {
          commit('clearPermission')
          throw Error('无有效可访问的系统资源')
        }
      })
    },
    // 重新加载路由
    reloadRouter ({ commit }, menus) {
      return new Promise(resolve => {
        // 平铺菜单
        const menuMap = {}
        Utils.tileTree(menuMap, menus, 'route')
        // 根据菜单树构建动态路由
        const asyncRoute = filterAsyncRoute(asyncRoutes, menuMap)
        asyncRoute.forEach(route => {
          router.addRoute(route)
        })
        commit('setAsyncRoute', asyncRoute)
        // 设置权限生成标志位
        commit('setGenerated', true)
        resolve(asyncRoute)
      })
    },
    // 清除权限
    clearPermission ({ commit }) {
      commit('clearPermission')
    }
  }
}

/**
 * 根据菜单树构建动态路由
 * @param asyncRoutes 所有异步路由
 * @param menuMap 平铺菜单Map
 */
function filterAsyncRoute (asyncRoutes, menuMap) {
  return asyncRoutes.filter(asyncRoute => {
    // 自身不存在，但是子集中有存在的router也要增加
    if (Utils.isValid(asyncRoute.children)) {
      asyncRoute.children = filterAsyncRoute(asyncRoute.children, menuMap)
      if (Utils.isValid(asyncRoute.children)) {
        return true
      }
    }
    // 在菜单中存在的router需要增加
    const menu = menuMap[asyncRoute.name]
    if (Utils.isValid(menu)) {
      asyncRoute.meta = { title: menu.name, keepAlive: menu.keepAlive || false, hidden: menu.hidden === 1 }
      return true
    }
  })
}

export default permission

