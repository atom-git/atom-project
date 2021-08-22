import $api from '@/api'
/**
 * 系统用户相关
 * token: 用户访问令牌
 * userInfo: 用户信息
 * openRoutes: 已打开的路由列表
 */
const user = {
  state: {
    token: null,
    userInfo: {},
    openRoutes: []
  },
  mutations: {
    setToken: (state, token) => {
      state.token = token
    },
    setUserInfo: (state, userInfo) => {
      state.userInfo = userInfo
    },
    setOpenRoutes: (state, openRoutes) => {
      state.openRoutes = openRoutes
    },
    clearUser: (state) => {
      state.token = null
      state.userInfo = {}
      state.openRoutes = []
    }
  },
  actions: {
    // 登录
    signIn ({ commit }, signUser) {
      return new Promise((resolve, reject) => {
        $api.system.signIn(signUser).then(token => {
          // 写入token
          commit('setToken', token)
          resolve(token)
        }).catch(error => {
          // 业务异常已在axios公共处理
          reject(error)
        })
      })
    },
    // 登出
    signOut ({ commit }) {
      return new Promise(resolve => {
        $api.system.signOut().then(() => {
          resolve()
        }).finally(() => {
          // 清空用户信息
          commit('clearUser')
        })
      })
    },
    // 清空用户
    clearUser ({ commit }) {
      commit('clearUser')
    },
    // 获取用户信息
    getUser ({ commit }) {
      return new Promise((resolve, reject) => {
        // 请求api
        $api.system.user.me().then(userInfo => {
          commit('setUserInfo', userInfo)
          resolve({
            menus: userInfo && userInfo.sysMenuTree,
            actions: userInfo && userInfo.sysActionList,
            appConfig: userInfo && userInfo.appConfig
          })
        }).catch(error => {
          // 业务异常已在axios公共处理
          reject(error)
        })
      })
    },
    // 设置openRoutes打开的路由
    setOpenRoutes ({ commit }, openRoutes) {
      commit('setOpenRoutes', openRoutes)
    }
  }
}

export default user

