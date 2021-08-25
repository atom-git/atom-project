<template>
  <FuncTable ref="funcTable"
             :apiUrl="apiUrl"
             :columns="columns"
             :funcZone="funcZone">
    <template #action="{ row }">
      <TipButtonGroup :actions="initActions(row)" @click="handleRowAction($event, row)"></TipButtonGroup>
    </template>
  </FuncTable>
</template>

<script>
/**
 * 提醒消息待办管理
 */
import { FuncTable } from '@/components/Common/FuncTable'
import { TipButtonGroup } from '@/components/Common/FuncButton'
export default {
  name: 'SysNews',
  components: { FuncTable, TipButtonGroup },
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
          form: { filter: true, default: 1 },
          // hidden: true
        },
        {
          dataIndex: 'action',
          title: '操作',
          slots: { customRender: 'action' }
        }
      ],
      // 功能按钮区域
      funcZone: {
        download: true,
        refresh: true,
        setting: true
      },
      // 当前编辑的消息
      sysNews: {}
    }
  },
  methods: {
    // 响应行级操作按钮
    handleRowAction (action, row) {
      this.sysNews = row
      // 标记未读
      if (action.name === 'unread') {
        if (row.status === 1) {
          this.$api.system.news.unread(row.id).then(() => {
            this.$message.success('消息标记为未读！')
            row.status = 0
          })
        }
      } else if (action.name === 'read') {
        // 标记已读
        if (row.status === 0) {
          this.$api.system.news.read(row.id).then(() => {
            this.$message.success('消息标记为已读！')
            row.status = 1
          })
        }
      } else if (action.name === 'delete') {
        // 删除
        this.$api.system.news.delete(row.id).then(() => {
          this.$message.success('消息已删除！')
          this.$refs.funcTable.refresh()
        })
      }
    },
    // 初始化操作按钮，根据当前状态来进行设置
    initActions (row) {
      return [
        { title: row && row.status ? '标记未读' : '标记已读', name: row && row.status ? 'unread' : 'read' },
        { title: '删除', name: 'delete', disabled: !row.ifValid, apiUrl: '/system/news/delete' }
      ]
    }
  }
}
</script>
