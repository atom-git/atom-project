/*
 * 提供store的getter方法
 * title: 应用名称
 * primaryColor: 主题色
 * theme: 主题
 * layout: 整体布局方式
 * multiTab: 多标签页是否开启
 * multiTabDraggable: 多标签页是否可拖拽
 * openRoutes: 已打开的路由列表
 * fixHeader: 是否固定头部
 * device: 设备类型
 * transition: 页面切换动画
 * clientWidth: 浏览器可视窗口的宽度
 * clientHeight: 浏览器可视窗口的高度
 * contentHeight: 浏览器可视窗口内容的高度
 * loading: 路由是否加载中
 * waterMarkEnable: 水印是否打开
 * menus: 系统菜单列表由用户菜单权限生成
 * actions: 系统按钮级资源控制列表由用户资源权限生成
 * token: 用户访问令牌
 * userInfo: 用户信息
 */
export default {
  title: state => state.app.title,
  primaryColor: state => state.app.primaryColor,
  theme: state => state.app.theme,
  layout: state => state.app.layout,
  multiTab: state => state.app.multiTab,
  multiTabDraggable: state => state.app.multiTabDraggable,
  fixHeader: state => state.app.fixHeader,
  transition: state => state.app.transition,
  device: state => state.app.device,
  scrollTop: state => state.app.scrollTop,
  clientWidth: state => state.app.clientWidth,
  clientHeight: state => state.app.clientHeight,
  contentHeight: state => state.app.contentHeight,
  loading: state => state.app.loading,
  waterMarkEnable: state => state.app.waterMarkEnable,
  asyncRoute: state => state.permission.asyncRoute,
  menus: state => state.permission.menus,
  actions: state => state.permission.actions,
  generated: state => state.permission.generated,
  token: state => state.user.token,
  userInfo: state => state.user.userInfo,
  openRoutes: state => state.user.openRoutes
}
