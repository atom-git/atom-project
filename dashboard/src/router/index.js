/**
 * 路由router配置
 * createRouter: 创建路由
 * createWebHashHistory: 创建路由模式
 * constantRoutes: 通用常量路由
 * createGuards: 路由guards
 */
import { createRouter, createWebHistory } from 'vue-router'
import { defaultRoutes } from '@/router/modules'
import { createGuards } from './guards'

export const router = createRouter({
  history: createWebHistory(),
  routes: defaultRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

/**
 * router装配函数
 * @param app app实例
 */
export function setUpRouter (app) {
  // 增加router守卫响应
  createGuards(router)
  app.use(router)
}
