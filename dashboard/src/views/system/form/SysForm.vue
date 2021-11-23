<template>
  <!-- 列表 -->
  <FuncTable :apiUrl="apiUrl"
             :columns="columns"
             :funcZone="funcZone"
             @table-func-action="handleFuncAction"
             @table-row-action="handleRowAction"></FuncTable>
  <!-- 新增编辑弹窗 -->
  <Dialog v-model="visible"
          title="自定义表单设计器"
          :loading="loading"
          :isFull="true">
    <FormMaker :formMaker="sysForm"
               @maker-save="handleMakerSave"></FormMaker>
  </Dialog>
</template>

<script>
/**
 * 自定义表单管理
 */
import { FuncTable } from '@/components/Common/FuncTable'
import Dialog from '@/components/Common/Dialog'
import { FormMaker } from '@/components/Common/FuncForm'
export default {
  name: 'SysForm',
  components: { FuncTable, Dialog, FormMaker },
  data () {
    return {
      // 自定义表单请求url
      apiUrl: '/system/form/list',
      // 自定义表单功能按钮区域
      funcZone: {
        add: { extend: true },
        refresh: true,
        setting: true
      },
      // 字段列表
      columns: [
        { dataIndex: 'title', title: '标题', form: { filter: true }, format: 'formatTooltip' },
        { dataIndex: 'width', title: '宽度', format: 'formatProgress' },
        { dataIndex: 'dialogSize', title: '弹窗大小', format: 'formatText|{dialogSize}px' },
        {
          dataIndex: 'modifyTime',
          title: '修改日期',
          format: 'formatDate|YYYY-MM-DD HH:mm:ss',
          hidden: true
        },
        {
          dataIndex: 'creatorName',
          title: '创建人',
          hidden: true
        },
        {
          type: 'radio',
          dataIndex: 'ifValid',
          title: '状态',
          format: 'formatSwitch',
          options: [{ value: 1, title: '有效', status: 'success' }, { value: 0, title: '删除', status: 'error' }],
          form: { filter: true }
        },
        {
          dataIndex: 'action',
          title: '操作',
          format: 'formatAction|text',
          actions: [
            { title: '编辑', name: 'edit', extend: true },
            { title: '数据', name: 'data', extend: true }
          ]
        }
      ],
      // 当前系统自定义表单
      sysForm: { formConfig: {}, widgets: [] },
      // 弹窗是否显示
      visible: false,
      // 请求loading
      loading: false
    }
  },
  methods: {
    // 响应顶部功能按钮操作
    handleFuncAction (action) {
      console.log(action)
      this.visible = true
    },
    // 响应行级操作按钮
    handleRowAction (action, row) {
      console.log(action, row)
    },
    // 响应自定义表单的保存
    handleMakerSave () {
      console.log(this.sysForm)
    }
  }
}
</script>

