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


# 组件说明
FormMaker组件在组件间移动不报错需要修改
vuedraggable组件中的三个文件，参考如下修改
https://github.com/SortableJS/vue.draggable.next/pull/52
vuedraggable.umd.js,vuedraggable.common.js,vuedraggable.js三个文件手工改动
insertNodeAt(this.$el, evt.item, evt.oldIndex);
改成：
insertNodeAt(evt.from, evt.item, evt.oldIndex);

# 组件加载顺序
- 父组件：beforeCreate
- 父组件：created
- 父组件：beforeMount
- 子组件：beforeCreate
- 子组件：created
- 子组件：beforeMount
- 子组件：mounted
- 父组件：mounted

