<template>
  <FormatTable title="FormatTable"
               :columns="columns"
               :funcZone="funcZone"
               :dataSource="dataSource"
               @table-row-selection="handleRowSelection">
    <template #head>
      <a-avatar><template #icon><IconFont type="UserOutlined"/></template></a-avatar>
    </template>
  </FormatTable>
  <FormTable title="FormTable" :columns="columns" :funcZone="funcZone" :dataSource="dataSource"></FormTable>
</template>

<script>
/**
 * 基础列表示例
 */
import { FormatTable, FormTable } from '@/components/Advance/FuncTable'
export default {
  name: 'BasicTable',
  components: { FormatTable, FormTable },
  data () {
    return {
      // 用户功能按钮区域
      funcZone: {
        add: { apiUrl: '/system/user/update' },
        check: true,
        delete: true,
        download: true,
        refresh: true,
        setting: true
      },
      // 数据
      dataSource: [
        { head: 'default.png', account: 'admin', name: '管理员', company: '三六零科技有限公司', progress: 50, deptId: 1000, sysDept: { id: 1000, deptName: '管理平台' }, createTime: '2021-07-30 15:12:36', ifValid: 1 },
        { head: 'default.png', account: 'super', name: '超级管理员', company: 'Atom Project有限公司', progress: 80, deptId: 1000, sysDept: { id: 1000, deptName: '管理平台' }, createTime: '2021-07-30 15:12:36', ifValid: 1 }
      ],
      // 字段列表
      columns: [
        {
          title: '基本信息',
          children: [
            {
              dataIndex: 'head',
              title: '头像',
              format: 'formatAvatar',
              // slots: { customRender: 'head' }
            },
            {
              type: 'input',
              dataIndex: 'account',
              title: '帐号',
              form: { filter: true, add: true, edit: true, rules: [{ required: true }] }
            },
            {
              type: 'input',
              dataIndex: 'name',
              title: '名称',
              form: { filter: true, add: true, edit: true, rules: [{ required: true }] }
            }
          ]
        },
        { dataIndex: 'company', title: '公司', format: 'formatLink' },
        { dataIndex: 'progress', title: '进度', format: 'formatProgress|number', width: 120 },
        {
          type: 'treeSelect',
          dataIndex: 'deptId',
          title: '部门',
          format: 'formatObject|sysDept.deptName',
          form: { add: true, edit: true, rules: [{ required: true, type: 'any' }], treeData: [{ id: 1000, deptName: '管理平台', children: [{ id: 1001, deptName: '市应急管理局' }, { id: 1002, deptName: '区应急管理局' }]}],
            replaceFields: this.$api.system.dept.replaceFields }
        },
        {
          dataIndex: 'createTime',
          title: '创建日期',
          format: 'formatDate|YYYY年MM月DD日',
          hidden: true
        },
        {
          type: 'radio',
          dataIndex: 'ifValid',
          title: '状态',
          format: 'formatStatus',
          options: [{ value: 1, title: '启用', status: 'success' }, { value: 0, title: '禁用', status: 'error' }],
          form: { filter: true, edit: true, rules: [{ required: true, type: 'any' }] }
        },
        {
          dataIndex: 'action',
          title: '操作',
          format: 'formatAction|text',
          actions: [
            { title: '编辑', name: 'edit', apiUrl: '/system/user/update' },
            { title: '重置', name: 'reset' },
            { title: '赋权', name: 'permission', apiUrl: '/system/user/{s}' }
          ]
        }
      ]
    }
  },
  methods: {
    // 响应row选择改变
    handleRowSelection (selectedRowKeys, selectedRows) {
      console.log(selectedRowKeys, selectedRows)
    }
  }
}
</script>
