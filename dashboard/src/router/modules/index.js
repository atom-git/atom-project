/**
 * personalRouter: 个性化异步菜单路由
 * commonRouter: 公共认证后路由
 * constantRouter: 通用认证前路由
 * errorRouter: 访问失败路由
 * defaultRoutes: 默认路由
 */
import { asyncRouter, publicRouter } from './async'
import constantRoutes from './constant'
import errorRoutes from './error'
// 合并默认路由
const defaultRoutes = [...constantRoutes]
export {
  defaultRoutes,
  asyncRouter,
  publicRouter,
  errorRoutes
}
