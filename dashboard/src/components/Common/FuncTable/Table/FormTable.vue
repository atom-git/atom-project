<template>
  <!-- 筛选条件区域 -->
  <FormFilter v-if="filterFields && filterFields.length > 0"
              v-model="filterModel"
              :fields="filterFields"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              @submit="handleFilterSubmit"
              @reset="handleFilterReset">
    <!-- 把外部传入的form slot传入内部 -->
    <template v-for="slotName in formSlots" #[slotName]="{ field, model }">
      <slot :name="slotName" :field="field" :model="model"></slot>
    </template>
  </FormFilter>
  <!-- 表格主体区域 -->
  <FormatTable v-bind="$attrs"
               :title="tableTitle"
               :columns="columns"
               :funcZone="funcZone"
               :scroll="scroll"
               :resizable="resizable"
               :loading="loading"
               @table-row-action="handleRowAction"
               @table-func-action="handleFuncAction"
               @change="handleTableChange">
    <!-- 外部$slots传入的自定义挂载点 -->
    <template v-for="slotName in slotColumns" #[slotName]="{ text, record, index }">
      <slot :name="slotName" :text="text" :row="record" :index="index"></slot>
    </template>
  </FormatTable>
  <!-- 统一表单处理 -->
  <UpdateForm v-model="formModel"
              :visible="formVisible"
              :title="formTitle"
              :width="formWidth"
              :loading="formLoading"
              :fields="formFields"
              :formError="formError"
              @table-editor-submit="handleFormSubmit"
              @table-editor-cancel="handleFormCancel"></UpdateForm>
</template>

<script>
/**
 * 自动生成过滤条件，生成默认新增编辑表单，生成默认预览表单的表格
 * 比FormatTable增加的属性
 * title: 表格名称，新增编辑等的名称设置[String, Object{ table, add, edit }]，默认设置: title || 当前route的meta.title || '数据表'
 * 包含FormmatTable的全部属性
 *    columns属性扩展：
 *    show: [Boolean]属性，属于配置表格中是否展示该字段，同时可以在funcZone的setting中进行修改
 *    type: [String]全局的form表单类型配置
 *    form: [Object{ filter: [Boolean, Object], add: [Boolean, Object], edit: [Boolean, Object], slot: [String] }]
 *          filter:属性表示该字段可以被筛选，值为Boolean,Object,如果是Object其属性值需要合并到FieldRender的属性中
 *          add,edit:属性表示该字段可以被新增[add]或者编辑[edit]，值为Boolean,Object,如果是Object其属性值需要合并到FieldRender的属性中，包括请求的url
 *          rules: [Array{Object}]表单的校验规则
 *          others: 其他form-item的属性，如tree-select的treeData
 *          slot:表单项插槽,指定插槽名称
 * formMode: [String] 表单的展示形式modal, drawer
 * formAttrs: [Object] updateForm的属性绑定
 * scroll: 表格超过某个高度滚动，且这个高度为atom-table最小高度 { x: 300, y: 500 }
 * 事件[Event]:
 * table-filter: 过滤表格数据，对FomrFilter的model数据传递，submit，reset事件一样，都是进行过滤，只是查询条件不一样
 * table-change: 表格中分页、排序、筛选变化时触发
 * table-row-action: 单行action事件，对edit,delete(单行删除),detail进行默认响应
 * table-func-action: 表格整体功能按钮区响应，对add,download,delete(多行删除)进行默认响应
 * 完整的表格格式
 * 顶部
 * ============================筛选条件============================
 * 表格title====================================新增，下载等功能按钮区 | 其他默认功能按钮
 * ============================表格部分============================
 */
import { FormFilter } from '@/components/Common/FuncForm'
import FormatTable from './FormatTable'
import UpdateForm from '@/components/Common/FuncTable/Render/UpdateForm'
import filter from '@/components/Common/FuncTable/mixins/filter'
import update from '@/components/Common/FuncTable/mixins/update'
import upload from '@/components/Common/FuncTable/mixins/upload'
export default {
  name: 'FormTable',
  components: {
    FormFilter,
    FormatTable,
    UpdateForm
  },
  mixins: [filter, update, upload],
  props: {
    // 表格名称，新增编辑等的名称设置[String, Object{ table, add, edit }]，默认设置: title || 当前route的meta.title || '数据表'
    title: {
      type: [String, Object, Boolean],
      default: ''
    },
    // 表格字段是否可拖拽，默认是false
    resizable: {
      type: Boolean,
      default: false
    },
    /**
     * 表格列的配置描述，参考Ant Design/Table/Column相关配置
     * 如果存在表头合并的情况，则在column中使用children嵌套的形式进行配置
     * 增加了show属性用于控制是否在列表中显示
     */
    columns: {
      type: Array,
      required: true
    },
    // 顶部右侧功能按钮区
    funcZone: {
      type: Object,
      required: false
    },
    // 窗口可滚动区域
    scroll: {
      type: Object,
      required: false
    },
    // form表单的宽度
    formWidth: {
      type: Number,
      defalut: 720
    },
    // form表单的label,wrapper宽度分配
    labelCol: {
      type: Object,
      default: () => ({ xs: 24, sm: 6, md: 8, lg: 8, xl: 6, xxl: 5 })
    },
    // 是否加载中
    loading: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      // 用于存储当前的操作往外抛
      curAction: {},
      // form显隐
      formVisible: false,
      // form提交loading
      formLoading: false,
      // form提交时的错误提示，此错误为服务端返回错误
      formError: ''
    }
  },
  computed: {
    // 表格的名称
    tableTitle () {
      // 如果设置了title为false，则不显示表头
      if (this.title === false) {
        return ''
      }
      if (this.$utils.isObject(this.title)) {
        return this.title.table || this.$route.meta['title'] || '数据表'
      } else {
        return this.title || this.$route.meta['title'] || '数据表'
      }
    },
    // 表单的名称
    formTitle () {
      if (this.$utils.isObject(this.title)) {
        return this.title[this.formType] || this.tableTitle.concat('【', this.formType === 'add' ? '新增' : this.formType === 'edit' ? '编辑' : '导入', '】')
      } else {
        return this.tableTitle.concat('【', this.formType === 'add' ? '新增' : this.formType === 'edit' ? '编辑' : '导入', '】')
      }
    },
    // 需要为输入控件设置布局样式
    wrapperCol () {
      if (this.labelCol && this.labelCol.span) {
        return { span: 24 - this.labelCol.span }
      } else {
        return {
          xs: 24 - this.labelCol.xs || 0,
          sm: 24 - this.labelCol.sm || 0,
          md: 24 - this.labelCol.md || 0,
          xl: 24 - this.labelCol.xl || 0,
          xxl: 24 - this.labelCol.xxl || 0,
        }
      }
    },
    // 外部传入slots的列，最多允许存在一层对象封装，多层挂载不上，不采用column的配置原因是如果没有配置slot则不挂载
    slotColumns () {
      return Object.keys(this.$slots).filter(slotName => !this.formSlots.includes(slotName))
    }
  },
  created () {
    // 初始化FormTable
    this.initTable(this.columns)
  },
  watch: {
    // 对columns时监听响应
    columns: {
      handler (newValue) {
        // 重新初始化表对象
        this.initTable(newValue)
      },
      deep: true
    }
  },
  emits: ['table-change', 'table-row-action', 'table-func-action'],
  methods: {
    // 初始化FormTable
    initTable (columns) {
      // 过滤器重置
      this.filterFields = []
      this.filterSlots = []
      // 新增编辑表单重置
      this.addFields = []
      this.editFields = []
      this.addSlots = []
      this.editSlots = []
      // 初始化列的form属性
      this.initColumns(columns)
    },
    // 初始化FormTable的属性值
    initColumns (columns) {
      // 重新初始化表格
      columns.map(column => {
        // 过滤器和默认表单初始化
        if (column.form) {
          if (column.form.filter) {
            // 生成filter表单
            this.generateFilterForm(column)
          }
          if (column.form.add || column.form.edit) {
            // 生成数据编辑的表单
            this.generateUpdateForm(column)
          }
        }
        // 如果有子集，则递归调用
        if (this.$utils.isValid(column.children)) {
          column.children = this.initColumns(column.children)
        }
      })
      return columns
    },
    /**
     * 根据column和field构建FieldRender属性，优先级field>form>column
     * @param column 列属性，不能把column的属性合并进去，否则会造成污染
     * @param form 统一的form属性
     * @param field 字段属性配置可能是Object | Boolean，filter.add等可能仅配置为true
     * @param target 目标字段属性
     */
    generateColumnForm (column, form, field, target) {
      if (this.$utils.isObject(field)) {
        // 处理特殊重要的属性
        Object.assign(target, form, field, {
          type: field.type || column.type || 'text',
          label: field.label || column.title,
          name: field.name || column.dataIndex,
          options: field.options || column.options
        })
      } else {
        // field是true|false时
        if (field) {
          Object.assign(target, {
            type: column.type || 'text',
            label: column.title || column.dataIndex,
            name: column.dataIndex,
            options: column.options
          }, form)
        }
      }
    },
    /**
     * 响应功能区域操作按钮
     * @param action 响应事件的名称
     * @param extend 是否扩展功能，true | false，true事件直接上抛不做默认处理
     */
    handleFuncAction (action, extend) {
      this.curAction = action
      if (extend) {
        this.$emit('table-func-action', action)
      } else {
        if (action.name === this.$default.ACTION_ADD.name) {
          this.dataFormType = this.$default.ACTION_ADD.name
          this.formVisible = true
        } else if (action.name === this.$default.ACTION_REFRESH.name || action.name === this.$default.ACTION_DOWNLOAD.name) {
          this.$emit('table-func-action', action)
        }
      }
    },
    /**
     * 响应行级操作按钮
     * @param action 响应事件的对象
     * @param row 行数据
     * @param column 字段属性
     */
    handleRowAction (action, row, column) {
      this.curAction = action
      if (action.extend) {
        this.$emit('table-row-action', action, row, column)
      } else {
        // 默认编辑
        if (action.name === this.$default.ACTION_EDIT.name) {
          this.formType = this.$default.ACTION_EDIT.name
          this.formModel = row
          this.formVisible = true
        } else if (action.name === this.$default.ACTION_DELETE.name) {
          // 默认删除
          action.replaceFields = Object.assign({ id: 'id', name: 'name' }, column.form ? column.form.replaceFields || {} : {})
          const self = this
          action.messageTitle = this.tableTitle.concat('【', row[action.replaceFields.name], '】')
          this.$confirm({
            content: '确认要删除'.concat(action.messageTitle, '吗？'),
            onOk () {
              self.$emit('table-row-action', action, row, column)
            }
          })
        } else {
          // 对非默认处理的情况进行容错处理
          action.extend = true
          this.$emit('table-row-action', action, row, column)
        }
      }
    },
    // 响应pagination, sorter改变
    handleTableChange (pagination, filters, sorter) {
      this.$emit('table-change', pagination, filters, sorter)
    }
  }
}
</script>
