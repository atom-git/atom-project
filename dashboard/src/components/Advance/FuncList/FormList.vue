<template>
  <!-- 筛选条件区域 -->
  <FormFilter v-if="filterFields && filterFields.length > 0"
              ref="formFilter"
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
  <!-- 列表主体区域 -->
  <FormatList v-bind="$attrs"
              :title="title"
              :dataSource="dataSource"
              :columns="columns"
              :pagination="pagination"
              :loadMore="loadMore"
              :hasMore="hasMore"
              :funcZone="funcZone"
              :loading="loading"
              @list-func-action="handleFuncAction"
              @list-load-more="handleLoadMore"
              @list-row-selection="handleRowSelection"
              @list-row-action="handleRowAction"></FormatList>
  <!-- 统一表单处理 -->
  <FormDialog v-model="formModel"
              :visible="formVisible"
              :title="formTitle"
              :width="formWidth"
              :loading="formLoading"
              :fields="formFields"
              :formSlots="formSlots"
              :formError="formError"
              @form-editor-submit="handleFormSubmit"
              @form-editor-cancel="handleFormCancel">
    <!-- 把外部传入的form slot传入内部 -->
    <template v-for="slotName in formSlots" #[slotName]="{ field, model }">
      <slot :name="slotName" :field="field" :model="model"></slot>
    </template>
  </FormDialog>
</template>

<script>
/**
 * 带表单的格式化列表
 */
import { FormDialog, FormFilter } from '@/components/Advance/FuncForm'
import FormatList from './FormatList'
import list from './mixins/list'
import { createVNode } from 'vue'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
export default {
  name: 'FormList',
  components: { FormDialog, FormFilter, FormatList },
  mixins: [list],
  props: {
    // 列表标题
    title: {
      type: [String, Boolean],
      required: false
    },
    // 数据列表
    dataSource: {
      type: Array,
      required: false
    },
    /**
     * 字段列表 { key, title, dataIndex, span, class, format, form }
     * key: 为固定的list-meta所对应的字段
     * title: 表示对应的FormatList中fieldKeys被替换的key
     * dataIndex: 表示字段属性名
     * 其他与FormTable中完全一致
     */
    columns: {
      type: Array,
      required: true
    },
    // 分页信息
    pagination: {
      type: [Object, Boolean],
      default: () => ({ current: 1, pageSize: 10, total: 0, showSizeChanger: true })
    },
    // 是否展示加载更多，与pagination互斥，loadMore优先级高
    loadMore: {
      type: Boolean,
      default: false
    },
    // 是否还有更多数据
    hasMore: {
      type: Boolean,
      default: true
    },
    // 顶部右侧功能按钮区
    funcZone: {
      type: Object,
      required: false
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
      // 当前选中的行
      selectedRowKeys: [],
      // form显隐
      formVisible: false,
      // form提交loading
      formLoading: false,
      // form提交时的错误提示，此错误为服务端返回错误
      formError: ''
    }
  },
  computed: {
    // 表单的名称
    formTitle () {
      return (this.title || '').concat('【', this.formType === 'add' ? '新增' : '编辑', '】')
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
    }
  },
  watch: {
    // 监听外部传入的字段列表变化，初始化List属性
    columns: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.initList(newValue)
      }
    }
  },
  emits: ['list-func-action', 'list-load-more', 'list-row-selection', 'list-row-action'],
  methods: {
    // 初始化List属性
    initList (columns) {
      columns.forEach(column => {
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
      })
    },
    // 响应功能区域操作
    handleFuncAction (action, extend) {
      this.curAction = action
      if (extend) {
        this.$emit('list-func-action', action)
      } else {
        if (action.name === this.$default.ACTION.ADD.name) {
          this.formType = this.$default.ACTION.ADD.name
          this.formModel = this.$utils.deepClone(this.defaultModel)
          this.formVisible = true
        } else if (action.name === this.$default.ACTION.DELETE.name) {
          // 非扩展功能且有选中记录时弹出删除提醒
          if (this.$utils.isValid(this.selectedRowKeys)) {
            // FuncZone区域的删除操作为批量删除动作
            const self = this
            this.$modal.$confirm({
              icon: createVNode(ExclamationCircleOutlined),
              okType: 'danger',
              content: `确认要删除选中记录吗？`,
              onOk () {
                self.$emit('list-func-action', action)
              }
            })
          } else {
            this.$message('无选中记录！')
          }
        } else if (action.name === this.$default.ACTION.REFRESH.name || action.name === this.$default.ACTION.DOWNLOAD.name) {
          this.$emit('list-func-action', action)
        }
      }
    },
    // 响应加载更多
    handleLoadMore () {
      this.$emit('list-load-more')
    },
    // 响应行选择
    handleRowSelection (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.$emit('list-row-selection', selectedRowKeys, selectedRows)
    },
    // 响应扩展操作
    handleRowAction (action, row) {
      this.curAction = action
      if (action.extend) {
        this.$emit('list-row-action', action, row, this.fieldKeys)
      } else {
        // 默认编辑
        if (action.name === this.$default.ACTION.EDIT.name) {
          this.formType = this.$default.ACTION.EDIT.name
          this.formModel = row
          this.formVisible = true
        } else if (action.name === this.$default.ACTION.DELETE.name) {
          // 默认删除
          const self = this
          action.messageTitle = this.title.concat('【', row[this.fieldKeys.title], '】')
          this.$modal.$confirm({
            icon: createVNode(ExclamationCircleOutlined),
            okType: 'danger',
            content: `确认要删除${action.messageTitle}吗？`,
            onOk () {
              self.$emit('list-row-action', action, row, this.fieldKeys)
            }
          })
        } else {
          // 对非默认处理的情况进行容错处理
          action.extend = true
          this.$emit('list-row-action', action, row, this.fieldKeys)
        }
      }
    }
  }
}
</script>
