/**
 * 路由router拦截配置
 * NProgress: 进度条
 * RouteUtils: 路由工具类
 */
import NProgress from 'nprogress'
import '@/config/theme/nprogress.css'
import RouteUtils from '@/utils/route'
import { defaultRoutes } from '@/router/modules'
import { store } from '@/store'
import Utils from '@/utils'
/**
 * 生成系统访问白名单
 * constantRouter: 常量路由
 * errorRouter: 错误路由
 */
const whiteRoutes = RouteUtils.generateWhite(defaultRoutes)
/**
 * 创建路由guards
 * @param router vue-router实例
 */
export function createGuards (router) {
  /**
   * 路由前拦截
   */
  router.beforeEach((to) => {
    // progress未开始时执行
    if (!NProgress.isStarted()) {
      NProgress.start()
    }
    // 设置网站标题
    to.meta && to.meta.title && RouteUtils.setDomTitle(to.meta.title)
    // 判断是否是白名单
    if (whiteRoutes.includes(to.name)) {
      return true
    } else {
      // 非白名单用户判断用户是否已经登录
      if (Utils.isValid(store.getters.token)) {
        // 判断用户信息是否已存在
        if (store.getters.generated) {
          return true
        } else {
          // 拉取用户信息
          store.dispatch('getUser').then(({ menus, actions, appConfig }) => {
            // 设置App应用配置信息
            store.dispatch('setConfig', appConfig).then(() => {
              store.dispatch('loadRouter', { menus, actions }).then(() => {
                // 跳转至目标页面
                return true
              }).catch(() => { return { replace: true, name: 'signIn' } })
            })
          }).catch(() => { return { replace: true, name: 'signIn' } })
        }
      } else {
        return { replace: true, name: 'signIn' }
      }
    }
  })
  /**
   * 路由解析拦截
   */
  router.beforeResolve(() => {
    NProgress.set(0.5)
  })
  /**
   * 路由后拦截
   */
  router.afterEach(() => {
    NProgress.done()
  })
}
