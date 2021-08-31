/**
 * asyncRouter: 异步菜单路由
 * constantRouter: 通用常量路由
 * errorRouter: 访问失败路由
 * defaultRoutes: 默认路由
 */
import asyncRoutes from './async'
import constantRoutes from './constant'
import errorRoutes from './error'
// 合并默认路由
const defaultRoutes = [...constantRoutes]
export {
  defaultRoutes,
  asyncRoutes,
  errorRoutes
}
