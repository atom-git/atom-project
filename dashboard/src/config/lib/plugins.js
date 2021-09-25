/**
 * 引入
 * ant design vue UI组件
 * moment 日期控件
 */
import  {
  Affix, Anchor, AutoComplete, Alert, Avatar, BackTop, Badge, Breadcrumb, Button, Calendar, Card, Collapse, Carousel,
  Cascader, Checkbox, Col, DatePicker, Divider, Dropdown, Form, Icon, Input, InputNumber, Layout, List, LocaleProvider,
  Menu, Mentions, Modal, Pagination, Popconfirm, Popover, Progress, Radio, Rate, Row, Select, Slider, Spin, Statistic,
  Steps, Switch, Table, Transfer, Tree, TreeSelect, Tabs, Tag, TimePicker, Timeline, Tooltip, Upload, Drawer, Skeleton,
  Comment, ConfigProvider, Empty, Result, Descriptions, PageHeader, Space, Image, Typography,
  message, notification
} from 'ant-design-vue'
import * as antIcons from '@ant-design/icons-vue'
import IconFont from '@/components/Common/IconFont'
import moment from 'moment'
import 'moment/dist/locale/zh-cn'
import Draggable from 'vuedraggable'
import axios from './axios'
import stomp from './stomp'
import api from '@/api'
import Utils from '@/utils'
import Default from '@/config/default'

/**
 * 注入到vue对象
 * @param app
 */
export function setUpPlugins (app) {
  app.use(Affix).use(Anchor).use(AutoComplete).use(Alert).use(Avatar).use(BackTop).use(Badge).use(Breadcrumb).use(Button).use(Calendar).use(Card).use(Collapse).use(Carousel)
  app.use(Cascader).use(Checkbox).use(Col).use(DatePicker).use(Divider).use(Dropdown).use(Form).use(Icon).use(Input).use(InputNumber).use(Layout).use(List).use(LocaleProvider)
  app.use(Menu).use(Mentions).use(Modal).use(Pagination).use(Popconfirm).use(Popover).use(Progress).use(Radio).use(Rate).use(Row).use(Select).use(Slider).use(Spin).use(Statistic)
  app.use(Steps).use(Switch).use(Table).use(Transfer).use(Tree).use(TreeSelect).use(Tabs).use(Tag).use(TimePicker).use(Timeline).use(Tooltip).use(Upload).use(Drawer).use(Skeleton)
  app.use(Comment).use(ConfigProvider).use(Empty).use(Result).use(Descriptions).use(PageHeader).use(Space).use(Image).use(Typography)
  // 注册全部图标
  Object.keys(antIcons).forEach(key => {
    app.component(key, antIcons[key])
  })
  app.config.globalProperties.$antIcons = antIcons
  // 挂载全局的IconFont组件
  app.use(IconFont)
  // moment国际化
  moment.locale('zh-cn')
  app.use(moment)
  // 引入拖拽组件
  app.component('Draggable', Draggable)
  // 挂载提示类公共实例
  app.config.globalProperties.$message = message
  app.config.globalProperties.$notification = notification
  app.config.globalProperties.$modal = {
    $info: Modal.info,
    $success: Modal.success,
    $error: Modal.error,
    $warn: Modal.warn,
    $confirm: Modal.confirm,
    $destroyAll: Modal.destroyAll
  }
  // 挂载http网络请求
  app.config.globalProperties.$http = axios
  // 挂载websocket stomp网络请求
  app.config.globalProperties.$stomp = stomp
  // 挂载时间
  app.config.globalProperties.$moment = moment
  // 挂载全局api
  app.config.globalProperties.$api = api
  // 挂载全局工具类
  app.config.globalProperties.$utils = Utils
  // 挂载全局默认配置
  app.config.globalProperties.$default = Default
}
