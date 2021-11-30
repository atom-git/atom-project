<template>
  <!-- 列表 -->
  <FuncTable ref="funcTable"
             :apiUrl="apiUrl"
             :columns="columns"
             :funcZone="funcZone"
             @table-func-action="handleFuncAction"
             @table-row-action="handleRowAction"></FuncTable>
  <!-- 新增编辑弹窗 -->
  <Dialog v-model="visible"
          title="自定义表单设计器"
          :loading="loading"
          :footer="null"
          :isFull="true">
    <FormMaker v-model:formMaker="formMaker"
               :panel="panel"
               @maker-panel-change="handlePanelChange"
               @maker-save="handleMakerSave"></FormMaker>
  </Dialog>
</template>

<script>
/**
 * 自定义表单管理
 */
import { FuncTable } from '@/components/Advance/FuncTable'
import Dialog from '@/components/Common/Dialog'
import { FormMaker } from '@/components/Advance/FuncForm'
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
        { dataIndex: 'width', title: '宽度', format: 'formatProgress|number' },
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
      sysForm: {},
      // 画布类型
      panel: 'mac',
      // 需要绑定的对象
      formMaker: {},
      // 弹窗是否显示
      visible: false,
      // 请求loading
      loading: false
    }
  },
  methods: {
    // 响应顶部功能按钮操作
    handleFuncAction (action) {
      if (action.name === 'add') {
        // 初始化画布状态
        this.initFormMaker()
        this.visible = true
      }
    },
    // 响应行级操作按钮
    handleRowAction (action, row) {
      console.log(action, row)
      if (action.name === 'edit') {
        this.sysForm = row
        this.panel = row.panel
        this.formMaker.formConfig = JSON.parse(row.formConfig)
        this.formMaker.widgets = JSON.parse(row.widgets)
        this.visible = true
      } else if (action.name === 'data') {
        console.log('data 获取')
      }
    },
    // 响应画布切换
    handlePanelChange (panel) {
      this.panel = panel
    },
    // 响应自定义表单的保存
    handleMakerSave (formConfig, widgets) {
      this.loading = true
      // 创建自定义表单对象
      const sysForm = {
        id: this.sysForm.id || null,
        title: formConfig.title,
        panel: this.panel,
        width: formConfig.width,
        formConfig: JSON.stringify(formConfig),
        widgets: JSON.stringify(widgets)
      }
      this.$api.system.form.update(sysForm).then(() => {
        this.$message.success('自定义表单信息更新成功！')
        // 刷新数据
        this.$refs.funcTable.refresh()
        // 弹出层关闭
        this.visible = false
        // 初始化画布状态
        this.initFormMaker()
      }).finally(() => { this.loading = false })
    },
    // 初始化自定义表单
    initFormMaker () {
      this.sysForm = this.formMaker = {}
      this.panel = 'mac'
    }
  }
}
</script>

