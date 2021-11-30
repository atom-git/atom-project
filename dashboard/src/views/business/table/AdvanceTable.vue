<template>
  <FuncTable title="FuncTable"
             :apiUrl="apiUrl"
             :columns="columns"
             :funcZone="funcZone"
             @table-data-load="handleDataLoad"
             @table-func-action="handleFuncAction"
             @table-row-action="handleRowAction"
             @table-form-submit="handleFormSubmit"
             @table-form-cancel="handleFormCancel"></FuncTable>
  <TabTable title="TabTable"
            :tabs="tabs"
            @table-tab-change="handleTabChange"
            @table-data-load="handleDataLoad"
            @table-func-action="handleFuncAction"
            @table-row-action="handleRowAction"
            @table-form-submit="handleFormSubmit"
            @table-form-cancel="handleFormCancel"></TabTable>
</template>

<script>
/**
 * 高级列表示例
 */
import { FuncTable, TabTable } from '@/components/Advance/FuncTable'
export default {
  name: 'AdvanceTable',
  components: { FuncTable, TabTable },
  data () {
    return {
      // 组织架构列表
      sysDeptList: [],
      // 组织架构树
      sysDeptTree: [],
      // 替代字段
      replaceFields: this.$api.system.dept.replaceFields,
      // 当前组织对象
      sysDept: {},
      // 用户请求url
      apiUrl: '/system/user/list',
      // 用户功能按钮区域
      funcZone: {
        add: { apiUrl: '/system/user/update' },
        check: true,
        delete: true,
        download: true,
        refresh: true,
        setting: true
      },
    }
  },
  computed: {
    // 表格列
    columns () {
      return [
        {
          title: '基本信息',
          children: [
            {
              dataIndex: 'head',
              title: '头像',
              format: 'formatAvatar'
            },
            {
              type: 'input',
              dataIndex: 'account',
              title: '帐号',
              form: { filter: true, add: true, edit: true, rules: [{ required: true }] }
            },
            {
              type: 'input',
              dataIndex: 'phone',
              title: '手机号码',
              form: { filter: true, add: true, edit: true, rules: [{ required: true, type: 'mobile' }] }
            },
            {
              type: 'input',
              dataIndex: 'name',
              title: '名称',
              form: { filter: true, add: true, edit: true, rules: [{ required: true }] }
            }
          ]
        },
        {
          type: 'treeSelect',
          dataIndex: 'deptId',
          title: '部门',
          format: 'formatObject|sysDept.deptName',
          form: { add: true, edit: true, rules: [{ required: true, type: 'any' }], treeData: this.sysDeptTree, replaceFields: this.$api.system.dept.replaceFields }
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
    },
    // tab表格
    tabs () {
      return [
        {
          tab: { title: '用户信息' },
          table: {
            apiUrl: this.apiUrl,
            columns: this.columns,
            funcZone: this.funcZone
          }
        },
        {
          tab: { title: '角色信息' },
          table: {
            apiUrl: this.apiUrl,
            columns: this.columns,
            funcZone: this.funcZone
          }
        }
      ]
    }
  },
  methods: {
    // 加载组织机构树
    loadSysDeptTree () {
      this.loading = true
      this.$api.system.dept.tree().then(sysDeptList => {
        this.sysDeptList = sysDeptList
        // 转成树结构
        this.sysDeptTree = this.$utils.buildTree(sysDeptList, 'id', 'deptParent')
      }).finally(() => {
        this.loading = false
      })
    },
    // 响应tab切换
    handleTabChange (activeKey, tab) {
      console.log(activeKey, tab)
    },
    // 响应数据加载完成
    handleDataLoad (response, activeKey, tab) {
      console.log(response, activeKey, tab)
    },
    // 响应功能区域操作按钮
    handleFuncAction (action, activeKey, tab) {
      console.log(action, activeKey, tab)
    },
    // 响应行操作
    handleRowAction (action, row, column, activeKey, tab) {
      console.log(action, row, column, activeKey, tab)
    },
    // 响应表单提交
    handleFormSubmit (action, model, activeKey, tab) {
      console.log(action, model, activeKey, tab)
    },
    // 响应表单取消
    handleFormCancel (action, activeKey, tab) {
      console.log(action, activeKey, tab)
    }
  },
  created () {
    // 加载组织树数据
    this.loadSysDeptTree()
  }
}
</script>
