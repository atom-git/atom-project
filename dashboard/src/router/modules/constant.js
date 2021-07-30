import { SignLayout } from '@/layouts'
/**
 * 基础路由
 * signIn: 登陆
 * signUp: 注册
 * forget: 忘记密码
 * operResult: 注册、忘记密码操作结果
 * user/center: 个人中心
 * user/setting: 帐号设置
 * 404: 页面未找到错误
 * 500: 服务器异常页面错误
 */
export default [
  // 根路径自动映射至登录，如果已经登录，则路由会被重写至home
  {
    path: '/',
    redirect: '/sign/in'
  },
  // 认证相关路由
  {
    path: '/sign',
    name: 'sign',
    component: SignLayout,
    redirect: '/sign/in',
    children: [
      {
        path: 'in',
        name: 'signIn',
        component: () => import(/* webpackChunkName: 'sign' */ '@/views/system/sign/SignIn'),
        meta: { title: '登录' }
      },
      {
        path: 'up',
        name: 'signUp',
        component: () => import(/* webpackChunkName: 'sign' */ '@/views/system/sign/SignUp'),
        meta: { title: '注册' }
      },
      {
        path: 'forget',
        name: 'forget',
        component: () => import(/* webpackChunkName: 'sign' */ '@/views/system/sign/Forget'),
        meta: { title: '忘记密码' }
      }
    ]
  }
]
