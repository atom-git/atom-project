![logo](https://user-images.githubusercontent.com/76482041/146486469-01636297-c528-4160-bf0a-ce98f81ba007.png)

# Atom Project
基于Vue3.X,Ant Design Vue2.X,Springboot2.X构建的完整后台管理系统，具有多个自定义高阶组件，AXIOS请求拦截，动态路由，且完整的权限控制系统，具备多语言切换版本，完整的样式和布局自定义能力，文档暂未编写，但是各组件均能找到使用示例和完整的参数说明，相信我，会有N多惊喜功能等着你，完全开源免费
i18n多语言版本功能相对滞后可自行根据需要改造，如有更多需求可联系作者atomgit@sina.com!

国内链接：https://gitee.com/atomgit/atom-project/

# 版本说明
| branch  |  description |
|---|---|
| master  |  中文版本 |
|  i18n |  多语言版本 |

# 目录说明
| 目录  |  二级目录 |  功能描述 |
|:---|:---|:---|
| api  |  business |  业务相关api定义目录 |
| api  |  system |  系统相关功能定义目录 |
| assets  |  - |  较小的可以直接打包的图标类文件 |
| components  |  Advance |  高级组件 |
| components  |  Common |  通用组件 |
| components  |  Layout |  布局组件 |
| config  |  directives |  自定义指令 |
| config  |  lib |  lib库插件、设备相关、ws、网络请求axios配置目录 |
| config  |  mixins |  公共混入 |
| config  |  theme |  自定义样式及less变量定义处 |
| layouts  |  DashboardLayout.vue |  管理后台布局 |
| layouts  |  ErrorLayout.vue |  失败页面布局 |
| layouts  |  PageLayout.vue |  业务页面布局 |
| layouts  |  PortalLayout.vue |  门户页面布局 |
| layouts  |  ScreenLayout.vue |  大屏页面布局 |
| layouts  |  SideLayout.vue |  左右布局页面 |
| layouts  |  SignLayout.vue |  登录页面布局 |
| router  |  - |  路由配置 |
| router  |  modules |  异步路由、常量路由、异常路由 |
| store  |  - |  全局变量及缓存配置  |
| store  |  modules |  app、权限、用户 |
| utils  |  index.js |  通过工具类  |
| utils  |  route.js |  路由相关工具类 |
| views  |  business |  form、home、list、table等组件示例目录 |
| views  |  system |  部门、错误、文件、表单、日志、菜单、消息、角色、登录、字典、用户、个人中心、个人设置 |
| src  |  App.vue |  应用全局大小监听及应用入口  |
| src  |  bootstrap.js  |  应用启动配置  |
| src  |  main.js  |  vue实例化入口  |
| dashboard  |  public  |  外部引入文件、自动生成主题、tinymce汉化  |
| dashboard  |  theme.js  |  自定义主题生成  |
| dashboard  |  vue.config.js  |  vue及打包拆包配置  |
# 组件列表
| 组件类别  |  组件大类 |  组件名称  |  组件目录 |  组件说明 |  组件说明 |
|:---|:---|:---|:---|:---|:---|
| 公共组件  |  动画组件   |  Animate  |  @/components/Common/Animate |  组件路由动画切换 |  已完成 |
| 公共组件  |  弹窗组件   |  Dialog  |  @/components/Common/Dialog  |  封装Modal与Drawer保持api一致 |  已完成 |
| 公共组件  |  文件列表组件 |  FileList,File   |  @/components/Common/FileList    |  文件列表及操作集成 |  已完成 |
| 公共组件  |  表单项组件  |  ColorPicker  |  @/components/Common/FormItem/ColorPicker  |  颜色选择器 |  已完成 |
| 公共组件  |  表单项组件  |  FileUpload  |  @/components/Common/FormItem/FileUpload  |  文件上传器 |  已完成 |
| 公共组件  |  表单项组件  |  IconPicker  |  @/components/Common/FormItem/IconPicker  |  图标选择器 |  已完成 |
| 公共组件  |  表单项组件  |  IconRadio  |  @/components/Common/FormItem/IconRadio    |  图标单选器 |  已完成 |
| 公共组件  |  表单项组件  |  ImagePicker  |  @/components/Common/FormItem/ImagePicker    |  图片选择器 |  已完成 |
| 公共组件  |  表单项组件  |  MapPicker  |  @/components/Common/FormItem/MapPicker    |  地图选择器 |  待完成 |
| 公共组件  |  表单项组件  |  OptionTree  |  @/components/Common/FormItem/OptionTree    |  选项编辑器 |  已完成 |
| 公共组件  |  表单项组件  |  RichText  |  @/components/Common/FormItem/RichText    |  富文本编辑器 |  已完成 |
| 公共组件  |  表单项组件  |  TableSelect  |  @/components/Common/FormItem/TableSelect  |  表格选择器 |  待完成 |
| 公共组件  |  表单项组件  |  TagCheck  |  @/components/Common/FormItem/TagCheck    |  标签选择器 |  已完成 |
| 公共组件  |  通用标题组件  |  FuncTitle  |  @/components/Common/FuncTitle    |  通用标题组件集成跳转等功能 |  已完成 |
| 公共组件  |  扩展功能区  |  FuncZone  |  @/components/Common/FuncZone    |  Card,Tab等扩展功能区的按钮实现及事件 |  已完成 |
| 公共组件  |  统一图标组件  |  IconFont  |  @/components/Common/IconFont    |  图标扩展组件统一内置及自定义IconFont图标 |  已完成 |
| 公共组件  |  JSON可视化  |  JsonView  |  @/components/Common/JsonView    |  JSON可视化预览实现组件 |  已完成 |
| 公共组件  |  菜单样式树  |  MenuTree  |  @/components/Common/MenuTree    |  菜单样式树支持扩展操作功能及搜索 |  已完成 |
| 公共组件  |  单元格格式化  |  CellRender  |  @/components/Render/CellRender    |  单元格格式化组件，可复用于FuncList,FuncTable,FuncDesc等组件 |  已完成 |
| 公共组件  |  表单字段格式化  |  FieldRender  |  @/components/Render/FieldRender    |  表单字段格式化，用于表单的内部单元呈现及动态表单等组件 |  已完成 |
| 公共组件  |  字段自动校验规划生成  |  FieldRender  |  @/components/Render/FieldRules    |  字段自动校验规划生成 |  已完成 |
| 公共组件  |  按钮组件    |  TipButton,TipButtonGroup  |  @/components/Common/TipButton  |  文件列表及操作集成 |  已完成 |
| 高阶组件  |  自定义表单   |  FormMaker  |  @/components/Advance/FormMaker  |  完整的自定义表单功能，几乎可以满足所有需求 |  已完成 |
| 高阶组件  |  功能性描述表格   |  FuncDesc  |  @/components/Advance/FuncDesc  |  Desc描述表格的展示形式，支持自动的请求格式化 |  已完成 |
| 高阶组件  |  表单弹窗   |  FormDialog  |  @/components/Advance/FuncForm/FormDialog  |  自定义弹窗与自定义表单列表的组合方便写出各种编辑窗口 |  已完成 |
| 高阶组件  |  表单过滤器   |  FormFilter  |  @/components/Advance/FuncForm/FormFilter  |  自定义过滤条，通过配置即可完成过滤条的配置 |  已完成 |
| 高阶组件  |  列表式表单   |  FormList  |  @/components/Advance/FuncForm/FormList  |  通过配置即可完成复杂表单的配置，及数据双绑，支持多种自定义表单项组件 |  已完成 |
| 高阶组件  |  功能性列表   |  FuncList  |  @/components/Advance/FuncList  |  支持完整的List属性，通过apiUrl配置即可完成复杂的动态数据查询与修改编辑动作 |  已完成 |
| 高阶组件  |  功能性表格   |  FuncTable  |  @/components/Advance/FuncTable  |  支持完整的Table属性，通过apiUrl配置即可完成复杂的动态数据查询与修改编辑动作，实现元数据的查询编辑删除等动作不用1天，不用1小时，仅用10分钟即可搞定 |  已完成 |
| 布局组件  |  全局底栏   |  Footer  |  @/components/Layout/Footer  |  全局底栏  |  已完成 |
| 布局组件  |  顶栏右侧功能区   |  HeaderRight  |  @/components/Layout/HeaderRight  |  顶栏右侧功能区  |  已完成 |
| 布局组件  |  图标   |  Logo  |  @/components/Layout/Logo  |  图标  |  已完成 |
| 布局组件  |  菜单   |  Menu  |  @/components/Layout/Menu  |  菜单，侧边及顶边菜单  |  已完成 |
| 布局组件  |  多标签   |  MultiTab  |  @/components/Layout/MultiTab  |  多标签相关功能组件  |  已完成 |
| 布局组件  |  侧边触发器   |  SiderTrigger  |  @/components/Layout/SiderTrigger  |  侧边触发器  |  已完成 |
| 布局组件  |  移动端布局   |  DrawerLayout.vue  |  @/components/Layout/DrawerLayout.vue  |  移动端布局，适配移动端的侧边拉出  |  已完成 |
| 布局组件  |  混合布局   |  MixLayout.vue  |  @/components/Layout/MixLayout.vue  |  混合布局  |  已完成 |
| 布局组件  |  基础路由   |  RouteView.vue  |  @/components/Layout/RouteView.vue  |  基础路由具有动画及保活功能配置  |  已完成 |
| 布局组件  |  侧边布局   |  SiderLayout.vue  |  @/components/Layout/SiderLayout.vue  |  侧边布局  |  已完成 |
| 布局组件  |  顶栏布局   |  TopLayout.vue  |  @/components/Layout/TopLayout.vue  |  顶栏布局  |  已完成 |

# 赞助
如果您觉得这个项目还不错，请不要吝啬您的鼓励！
![alipay](https://user-images.githubusercontent.com/76482041/146486428-4d282f1e-fc93-4186-8fda-078f9e13b2f7.jpg)
![wechat](https://user-images.githubusercontent.com/76482041/146486411-0a42d6e5-0e67-4a2a-8e5c-e1e39e8a1751.jpg)

