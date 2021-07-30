/*
 * 应用启动时参数初始化，从用户缓存中进行拉取
 */
import { toggleTheme } from '@/config/theme'
import { setUpPlugins } from '@/config/lib/plugins'
import { setUpRouter, router } from '@/router'
import { setUpStore, store } from '@/store'
import { setUpDirectives } from '@/config/directives'

export function bootstrap (app) {
  return new Promise(resolve => {
    // 注册插件
    setUpPlugins(app)
    // 注册vuex store
    setUpStore(app)
    // 注册指令
    setUpDirectives(app)
    // 读取用户个性化配置包括布局，主题，主题色等信息
    store.dispatch('setLayout', store.getters.userInfo.layout || 'mix').then(() => { console.log('布局已切换') }),
    // 更新页面主题
    toggleTheme(store.state.theme, store.state.primaryColor).then(() => { console.log('主题已切换') })
    // 读取store中的配置信息判断用户是否登录【即是否是页面刷新】，如果登录则使用store.permission.asyncRoute生成异步路由
    if (store.getters.generated) {
      store.dispatch('reloadRouter', store.getters.menus).then(() => {
        // 注册路由
        setUpRouter(app)
        resolve(router)
      })
    } else {
      // 注册路由
      setUpRouter(app)
      resolve(router)
    }
  })
}
