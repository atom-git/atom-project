import { ErrorLayout } from '@/layouts'
/**
 * 错误页面
 * error: 错误页面
 * *[other]: 未匹配页面全部导航至错误页面
 */
export default [
  {
    path: '/error',
    name: 'error',
    component: ErrorLayout,
    redirect: '/error/404',
    children: [
      {
        path: '404',
        name: 'error404',
        component: () => import(/* webpackChunkName: 'error' */ '@/views/system/error/404'),
        meta: { title: '未找到页面' }
      },
      {
        path: '500',
        name: 'error500',
        component: () => import(/* webpackChunkName: 'error' */ '@/views/system/error/500'),
        meta: { title: '服务器错误' }
      }
    ]
  },
  {
    path: '/:other(.*)*',
    name: 'other',
    redirect: '/error/404'
  }
]
