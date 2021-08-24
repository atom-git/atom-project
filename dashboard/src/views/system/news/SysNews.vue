<template>
  <FuncTable :apiUrl="apiUrl"
             :columns="columns"
             :funcZone="funcZone"></FuncTable>
</template>

<script>
/**
 * 提醒消息待办管理
 */
import { FuncTable } from '@/components/Common/FuncTable'
export default {
  name: 'SysNews',
  components: { FuncTable },
  data () {
    return {
      // 请求url
      apiUrl: '/system/news/list',
      // 字段列表
      columns: [
        {
          dataIndex: 'title',
          title: '标题',
          form: { filter: true },
          format: 'formatTooltip'
        },
        {
          dataIndex: 'type',
          title: '类型',
          type: 'select',
          form: { filter: true },
          format: 'formatTag',
          options: [{ value: 1, title: '通知', color: 'success' }, { value: 2, title: '消息', color: 'processing' }, { value: 3, title: '待办', color: 'warning' }],
        },
        {
          dataIndex: 'fromUser',
          title: '来源',
          format: 'formatObject|fromSysUser.account',
          hidden: true
        },
        {
          dataIndex: 'toUser',
          title: '目标',
          format: 'formatObject|toSysUser.account'
        },
        // 仅用于表单的字段
        {
          form: { filter: { type: 'text', name: 'account', label: '用户帐号' } }
        },
        {
          dataIndex: 'route',
          title: '路由',
          hidden: true
        },
        {
          dataIndex: 'status',
          title: '消息状态',
          type: 'radio',
          format: 'formatStatus',
          options: [{ value: 1, title: '已读', status: 'success' }, { value: 0, title: '未读', status: 'processing' }],
          form: { filter: true }
        },
        {
          dataIndex: 'createTime',
          title: '消息时间',
          format: 'formatDate|YY/MM/DD HH:mm:ss'
        },
        {
          dataIndex: 'ifValid',
          title: '状态',
          type: 'radio',
          format: 'formatStatus',
          options: [{ value: 1, title: '有效', status: 'success' }, { value: 0, title: '删除', status: 'error' }],
          form: { filter: true },
          hidden: true
        }
      ],
      // 功能按钮区域
      funcZone: {
        download: true,
        refresh: true,
        setting: true
      }
    }
  }
}
</script>
