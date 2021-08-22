/*
 * 项目默认配置
 * platform: 平台用于区分用户
 * platformType: 平台类型用于区分应用场景，同平台用户多人登录的问题
 * title: 项目名称[自定义]
 * primaryColor: 主题色 [#1890ff]
 * theme: 主题 [dark, light, mix]
 * layout: 整体布局方式 [sider, top, mix(top-sider), drawer]
 * multiTab: 多标签页是否开启 [true, false]
 * multiTabDraggable: 多标签页是否可拖拽 [true, false]
 * fixHeader: 固定顶部 [true, false]
 * waterMark: 是否开启水印 [true, false]
 * transition: 路由切换动画[fade, zoom, slide, bounce] fade隐进隐退，zoom放大缩小， slide滑出滑进，bounce弹跳进出
 * dialog: type弹窗类型[modal, drawer],size弹窗大小
 * storeOptions: vuex-persistedstate持久化插件配置[key:存储中的key值，storage:使用什么存储]
 * colorSet: 主题颜色集
 * extendColorSet: 主题扩展颜色集
 * 其他常量配置
 */
export default {
  platform: 'dashboard',
  platformType: 'WEB',
  title: 'Atom Pro',
  primaryColor: '#5B8FF9',
  theme: 'mix',
  layout: 'sider',
  mobileLayout: 'drawer',
  multiTab: true,
  multiTabDraggable: false,
  fixHeader: true,
  waterMark: false,
  transition: {
    name: 'slide',
    direction: 'Right',
    disabled: false
  },
  dialog: {
    type: 'drawer',
    size: 520
  },
  storeOptions: {
    key: '_ATOM',
    storage: window.localStorage
  },
  colorSet: ['#5B8FF9', '#5AD8A6', '#5D7092', '#F6BD16', '#E8684A', '#6DC8EC', '#9270CA', '#FF9D4D', '#269A99', '#FF99C3'],
  extendColorSet: ['#BDD2FD', '#BDEFDB', '#C2C8D5', '#FBE5A2', '#F6C3B7', '#B6E3F5', '#D3C6EA', '#FFD8B8', '#AAD8D8', '#FFD6E7'],
  // 系统首页
  HOME_PAGE: 'dashboard',
  // WebSocket连接endpoint
  WEB_SOCKET_ENDPOINT: '/stomp',
  // HTTP方法常量
  HTTP_METHOD_GET: 'GET',
  HTTP_METHOD_POST: 'POST',
  HTTP_METHOD_PUT: 'PUT',
  HTTP_METHOD_DELETE: 'DELETE',
  // 系统运转的功能性常量
  ACTION_ADD: { icon: 'FileAddOutlined', title: '新增', name: 'add' },
  ACTION_EDIT: { icon: 'FormOutlined', title: '编辑', name: 'edit' },
  ACTION_DELETE: { icon: 'DeleteOutlined', title: '删除', name: 'delete' },
  ACTION_UPLOAD: { icon: 'CloudUploadOutlined', title: '导入', name: 'upload' },
  ACTION_DOWNLOAD: { icon: 'CloudDownloadOutlined', title: '下载', name: 'download' },
  ACTION_REFRESH: { icon: 'ReloadOutlined', title: '刷新', name: 'refresh' },
  ACTION_SETTING: { icon: 'SettingOutlined', title: '列设置', name: 'setting' },
  ACTION_MOVE_UP: { icon: 'ArrowUpOutlined', title: '上移', name: 'moveup' },
  ACTION_MOVE_DOWN: { icon: 'ArrowDownOutlined', title: '下移', name: 'movedown' }
}
