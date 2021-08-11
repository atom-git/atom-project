<template>
  <!-- 数据字典列表 -->
  <FuncTable :apiUrl="apiUrl"
             :columns="columns"
             :funcZone="funcZone"
             @table-row-action="handleRowAction"></FuncTable>
  <!-- 系统维值列表 -->
  <SysTypeCode v-model="visible" :sysType="sysType"></SysTypeCode>
</template>

<script>
/**
 * 数据字典管理
 */
import { FuncTable } from '@/components/Common/FuncTable'
import SysTypeCode from './SysTypeCode'
export default {
  name: 'SysType',
  components: { FuncTable, SysTypeCode },
  data () {
    return {
      // 请求url
      apiUrl: '/system/type/list',
      // 表格列
      columns: [
        {
          dataIndex: 'meanName',
          title: '字典名称',
          form: { filter: true, add: true, edit: true, rules: [{ required: true }] }
        },
        {
          dataIndex: 'meanDesc',
          title: '字典描述',
          form: { add: true, edit: true, rules: [{ required: true }] }
        },
        {
          type: 'radio',
          dataIndex: 'multiLevel',
          format: 'formatStatus',
          title: '字典层级',
          options: [{ value: 0, title: '单层级', status: 'warning' }, { value: 1, title: '多层级', status: 'processing' }],
          form: { add: true, edit: true, mode: 'button', buttonStyle: 'solid', default: 0 }
        },
        {
          dataIndex: 'action',
          title: '操作',
          format: 'formatAction|text',
          actions: [
            { title: '编辑', name: 'edit', apiUrl: '/system/type/update' },
            { title: '配置', name: 'setting' },
            { title: '删除', name: 'delete', apiUrl: '/system/type/delete' }
          ],
          form: { replaceFields: { name: 'meanName' } }
        }
      ],
      // 功能按钮区域
      funcZone: {
        add: { apiUrl: '/system/type/update' },
        refresh: true,
        setting: true
      },
      // 当前编辑的字典类型
      sysType: {},
      // 类型的数据字典列表
      sysTypeCodeList: [],
      // 请求loading
      loading: false,
      // dialog是否显示
      visible: false
    }
  },
  methods: {
    // 响应行级操作按钮
    handleRowAction (action, row) {
      this.sysType = row
      // 配置系统维值
      if (action.name === 'setting') {
        this.visible = true
      }
    }
  }
}
</script>
