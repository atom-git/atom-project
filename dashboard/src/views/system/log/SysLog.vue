<template>
  <FuncTable :apiUrl="apiUrl"
             :columns="columns"
             :funcZone="funcZone"></FuncTable>
</template>

<script>
/**
 * 日志管理
 */
import { FuncTable } from '@/components/Common/FuncTable'
export default {
  name: 'SysLog',
  components: { FuncTable },
  data () {
    return {
      // 请求url
      apiUrl: '/system/log/list',
      // 字段列表
      columns: [
        {
          dataIndex: 'account',
          title: '账号',
          form: { filter: true },
          format: 'formatTooltip'
        },
        {
          dataIndex: 'name',
          title: '昵称',
          format: 'formatTooltip'
        },
        {
          dataIndex: 'type',
          type: 'select',
          title: '类型',
          format: 'formatStatus',
          options: [{ value: 1, title: '认证登录', status: 'success' }, { value: 2, title: '服务调用', status: 'processing' }, { value: 3, title: '数据同步', color: 'cyan' }],
          form: { filter: true }
        },
        {
          dataIndex: 'createTime',
          type: 'rangePicker',
          title: '请求时间',
          format: 'formatDate|YY/MM/DD HH:mm:ss',
          form: { filter: { name: 'logTimeRange', showTime: true } }
        },
        {
          dataIndex: 'platformType',
          title: '请求平台'
        },
        {
          dataIndex: 'requestUrl',
          title: '请求url',
          format: 'formatTooltip'
        },
        {
          dataIndex: 'requestParams',
          title: '请求参数',
          format: 'formatTooltip'
        },
        {
          dataIndex: 'resultStatus',
          type: 'select',
          title: '请求状态',
          format: 'formatStatus',
          options: [{ value: 200, title: '成功', status: 'success' }, { value: 400, title: '失败', status: 'error' }, { value: 500, title: '异常', status: 'warning' }],
          form: { filter: true }
        },
        // 仅用于表单的字段
        {
          form: { filter: { type: 'text', name: 'keyword', label: '关键词' } }
        },
        {
          dataIndex: 'executionTime',
          title: '请求时长',
          format: 'formatText|{executionTime}ms'
        },
        {
          dataIndex: 'exception',
          title: '异常',
          hidden: true,
          format: 'formatTooltip'
        },
        {
          dataIndex: 'result',
          title: '结果',
          width: 200,
          hidden: true,
          format: 'formatTooltip'
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
