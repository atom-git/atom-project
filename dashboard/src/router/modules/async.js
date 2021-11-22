import { DashboardLayout, PageLayout } from '@/layouts'
/**
 * 异步路由，由服务端决定meta属性[title, keepAlive, hidden]
 * webpackChunkName: 分离文件包
 */
export default [
  {
    path: '/',
    name: 'dashboard',
    component: DashboardLayout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import(/* webpackChunkName: 'home' */ '@/views/business/home/Home'),
        meta: { title: '首页' }
      },
      {
        path: 'table',
        name: 'table',
        component: PageLayout,
        meta: { title: '列表展示' },
        children: [
          {
            path: 'basic',
            name: 'basicTable',
            component: () => import(/* webpackChunkName: 'table' */ '@/views/business/table/BasicTable'),
            meta: { title: '基础列表' }
          },
          {
            path: 'advance',
            name: 'advanceTable',
            component: () => import(/* webpackChunkName: 'table' */ '@/views/business/table/AdvanceTable'),
            meta: { title: '高级列表' }
          },
          {
            path: 'card',
            name: 'cardTable',
            component: () => import(/* webpackChunkName: 'table' */ '@/views/business/table/CardTable'),
            meta: { title: '卡片列表' }
          }
        ]
      },
      {
        path: 'form',
        name: 'form',
        component: PageLayout,
        meta: { title: '表单呈现' },
        children: [
          {
            path: 'basic',
            name: 'basicForm',
            component: () => import(/* webpackChunkName: 'form' */ '@/views/business/form/BasicForm'),
            meta: { title: '基础表单' }
          },
          {
            path: 'step',
            name: 'stepForm',
            component: () => import(/* webpackChunkName: 'form' */ '@/views/business/form/StepForm'),
            meta: { title: '分步表单' }
          },
          {
            path: 'advance',
            name: 'advanceForm',
            component: () => import(/* webpackChunkName: 'form' */ '@/views/business/form/AdvanceForm'),
            meta: { title: '高级表单' }
          },
          {
            path: 'dynamic',
            name: 'dynamicForm',
            component: () => import(/* webpackChunkName: 'form' */ '@/views/business/form/DynamicForm'),
            meta: { title: '动态表单' }
          }
        ]
      },
      {
        path: 'system',
        name: 'system',
        component: PageLayout,
        meta: { title: '系统管理' },
        children: [
          {
            path: 'sysdept',
            name: 'sysdept',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/dept/SysDept'),
            meta: { title: '组织管理' }
          },
          {
            path: 'sysuser',
            name: 'sysuser',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/user/SysUser'),
            meta: { title: '用户管理' }
          },
          {
            path: 'sysrole',
            name: 'sysrole',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/role/SysRole'),
            meta: { title: '角色管理' }
          },
          {
            path: 'sysmenu',
            name: 'sysmenu',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/menu/SysMenu'),
            meta: { title: '菜单管理' }
          },
          {
            path: 'systype',
            name: 'systype',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/type/SysType'),
            meta: { title: '数据字典' }
          },
          {
            path: 'sysform',
            name: 'sysform',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/form/SysForm'),
            meta: { title: '表单管理' }
          },
          {
            path: 'sysnews',
            name: 'sysnews',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/news/SysNews'),
            meta: { title: '消息管理' }
          },
          {
            path: 'sysfile',
            name: 'sysfile',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/file/SysFile'),
            meta: { title: '文件管理' }
          },
          {
            path: 'syslog',
            name: 'syslog',
            component: () => import(/* webpackChunkName: "system" */ '@/views/system/log/SysLog'),
            meta: { title: '日志管理' }
          },
          {
            path: 'center',
            name: 'userCenter',
            component: () => import(/* webpackChunkName: 'system' */ '@/views/system/user/UserCenter'),
            meta: { title: '个人中心' }
          }
        ]
      }
    ]
  }
]
