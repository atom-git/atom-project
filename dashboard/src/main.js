import { createApp } from 'vue'
import App from './App.vue'
import { bootstrap } from '@/bootstrap'
// 创建app
const app = createApp(App)

// 启动初始化
bootstrap(app).then(router => {
  // 路由准备就绪后挂载APP实例
  router.isReady().then(() => app.mount('#app'))
})
