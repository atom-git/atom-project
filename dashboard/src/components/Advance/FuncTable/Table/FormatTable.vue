<template>
  <a-card class="atom-table" :bordered="false">
    <!-- 标题 -->
    <template v-if="title" #title>
      <FuncTitle :title="title"></FuncTitle>
    </template>
    <!-- 右侧功能按钮，数据表的checkall由表格进行控制 -->
    <template v-if="funcZone" #extra>
      <FuncZone :funcZone="funcZone"
                :checkallShow="false"
                :checkedCount="selectedRowKeys.length"
                :columns="renderColumns"
                :titleSlots="titleSlots"
                @func-zone-action="handleFuncAction"
                @func-zone-clear-check="handleFuncClearCheck"
                @column-change="handleColumnChange">
        <!-- 外部$slots传入的自定义挂载点 -->
        <template v-for="slotName in titleSlots" #[slotName]>
          <slot :name="slotName"></slot>
        </template>
      </FuncZone>
    </template>

    <!-- 表格顶部提示区域 -->
    <template v-if="$slots.alert">
      <slot name="alter"></slot>
    </template>
    <a-alert v-if="alert && alert.message"
             :message="alert.message" :type="alert.type" :closable="alert.closable || false" />

    <!-- 表格主体区域 -->
    <a-table v-if="showColumns && showColumns.length > 0"
             v-bind="$attrs"
             :class="textCenter ? 'center' : ''"
             :scroll="scroll"
             :columns="showColumns"
             :dataSource="dataSource"
             :rowKey="generateRowKey"
             :rowSelection="checkable ? { selectedRowKeys: selectedRowKeys, onChange: onRowSelectChange } : null"
             :size="size"
             :bordered="bordered"
             :loading="loading"
             v-column-resize="resizable">
      <!-- 外部$slots传入的自定义挂载点 -->
      <template v-for="slotName in slotColumns" #[slotName]="{ text, record, index }">
        <slot :name="slotName" :text="text" :row="record" :index="index"></slot>
      </template>
      <!-- 内置格式化字段 -->
      <template v-for="column in formatColumns" :key="column.dataIndex" #[column.slots.customRender]="{ text, record, index }">
        <CellRender :column="column"
                      :text="text"
                      :row="record"
                      :index="index"
                      @cell-action="handleRowAction"></CellRender>
      </template>
    </a-table>
    <a-empty v-else description="无可展示列"/>
  </a-card>
</template>

<script>
/**
 * 格式化表格
 * 内置格式化参考FormatColumn的配置
 * columns属性扩展：
 * show: [Boolean]属性，属于配置表格中是否展示该字段，同时可以在funcZone的setting中进行修改
 * 事件[Event]:
 * table-row-action: 单行action事件
 * table-func-action: 上部功能按钮事件
 * funcZone: [Object{ TipButton属性 }] 新增，下载等功能按钮区，具备默认实现逻辑 [add, download, upload, refresh, setting]
 *           如果需要自定义，action事件名称不能与默认一致，默认事件包括[新增:add,编辑:edit,下载:download,导入:upload,删除:delete,详情:detail]
 *           示例:
 *           {
 *              [add: { ...TipButton属性, permission, extend: true|false }], // 可选配置 | Boolean, extend是true，表示需要外部扩展响应，取消默认响应
 *              [edit: { ...TipButton属性, permission, extend: true|false }], // 可选配置 | Boolean, extend是true，表示需要外部扩展响应，取消默认响应
 *              ...
 *           }
 *           upload: 导入按钮点击后，如查有提供模板地址，则生成导入模板的下载提示
 *           默认功能按钮包括，refresh 刷新 可选[定时刷新], setting 列设置
 */
import FuncTitle from '@/components/Common/FuncTitle'
import FuncZone from '@/components/Common/FuncZone'
import { CellRender } from '@/components/Common/Render'
export default {
  name: 'FormatTable',
  components: { FuncTitle, FuncZone, CellRender },
  props: {
    // 数据表头
    title: {
      type: String,
      required: false
    },
    // 表格的尺寸大小，默认是middle，可以是default | middle | small
    size: {
      type: String,
      default: 'middle'
    },
    // 窗口可滚动区域
    scroll: {
      type: Object,
      required: false
    },
    // 文本是否居中对齐，默认是是居中，否是左对齐
    textCenter: {
      type: Boolean,
      default: true
    },
    // 是否显示边框，默认显示
    bordered: {
      type: Boolean,
      default: true
    },
    // 表格是否可拖拽，默认是false
    resizable: {
      type: Boolean,
      default: false
    },
    /**
     * 表格列的配置描述，参考Ant Design/Table/Column相关配置
     * 如果存在表头合并的情况，则在column中使用children嵌套的形式进行配置
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
    /**
     * 表格数据，为空时显示无数据
     */
    dataSource: {
      type: Array,
      required: false
    },
    /**
     * 表行数据rowKey或者生成规则，默认是id，若不存在id，则取record对应的index
     */
    rowKey: {
      type: [String, Function],
      default: 'id'
    },
    // 顶部提示区域
    alert: {
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
      // 筛选需要格内部式化的列
      formatColumns: [],
      // funcZone中选中的列
      checkedColumns: [],
      // 是否可选择
      checkable: false,
      // 当前选中的行
      selectedRowKeys: [],
      // 标题相关挂载点
      titleSlots: []
    }
  },
  computed: {
    // 外部传入slots的列，由column明确slot形式
    slotColumns () {
      return Object.keys(this.$slots)
    },
    // 用于渲染的自身的Columns，写在data中防止非响应属性，通过深度clone防止同一table参数互相影响
    renderColumns () {
      return this.initRenderColumns(this.$utils.deepClone(this.columns))
    },
    // 用于表格显示的列
    showColumns () {
      return this.initShowColumns(this.$utils.deepClone(this.renderColumns))
    }
  },
  watch: {
    // 对columns时监听响应
    columns: {
      handler () {
        // 重置相关字段
        this.formatColumns = []
        this.titleSlots = []
      },
      deep: true
    }
  },
  emits: ['table-row-action', 'table-func-action', 'table-row-selection'],
  methods: {
    /**
     * 生成渲染用的Columns
     */
    initRenderColumns (columns) {
      const renderColumns = []
      columns.map(column => {
        // dataIndex, key, title存在任何一项时，才作为表格字段，否则只用于传入form表单使用
        if (column.dataIndex || column.key || column.title) {
          this.generateSlot(column)
          if (this.$utils.isValid(column.children)) {
            column.children = this.initRenderColumns(column.children)
          }
          // 如果是操作字段或者是formatAction时，其字段不做超出隐藏
          if (column.dataIndex === 'action') {
            column.class = 'table-action-td'
          }
          // 格式化为进度条时样式特殊定义
          if (column.format && column.format.indexOf('formatProgress') >= 0) {
            column.class = 'table-format-progress'
          }
          renderColumns.push(column)
        }
      })
      return renderColumns
    },
    /**
     * 初始化用于显示的列
     */
    initShowColumns (columns) {
      const showColumns = []
      columns.map(column => {
        if (this.$utils.isValid(column.children)) {
          column.children = this.initShowColumns(column.children)
        }
        // 根据checkedColumns控制字段是否显示，如果未定义funcZone或者funcZone中setting为false则直接全部显示
        if (this.checkedColumns.includes(column.key) || (!this.funcZone || !this.funcZone.setting)) {
          showColumns.push(column)
        }
      })
      return showColumns
    },
    /**
     * 根据传入的参数重新结构化slots参数
     * format参数设置customRender渲染slot，以format_slot_${column.dataIndex}命名
     */
    generateSlot (column) {
      // 初始化字段
      column.dataIndex = column.dataIndex || column.key || column.title
      column.key = column.key || column.dataIndex || column.title
      column.slots = column.slots || {}
      // 如果字段有formatTooltip属性，则设置ellipsis为true
      column.ellipsis = column.ellipsis || column.format === 'formatTooltip'
      // 如果有设置ellipsis=true自动增加column.format === 'formatTooltip'配置
      if (column.ellipsis) {
        column.format = column.format || 'formatTooltip'
      }
      if (column.format) {
        // 设置format时如果外部有传入slots则以传入优先级最高，如果没有则使用内部格式化
        if (!column.slots.customRender) {
          column.slots.customRender = `format_slot_${column.dataIndex}`
          this.formatColumns.push(column)
        }
      }
      // 如果外部有自定义标题，则生成titleSlots用于对treenode的挂载
      if (column.slots && column.slots.title) {
        this.titleSlots.push(column.slots.title)
      }
    },
    /**
     * 生成行rowKey，默认按照id，如果没有id属性，则按照内置row-key自动生成
     * @returns {string|(function())}
     */
    generateRowKey (record, index) {
      if (this.$utils.isValid(record) && record[this.rowKey]) {
        return record[this.rowKey]
      } else {
        return index
      }
    },
    // 行选择变化
    onRowSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.$emit('table-row-selection', selectedRowKeys, selectedRows)
    },
    /**
     * 响应功能区域操作按钮
     * @param action 响应事件的名称
     * @param extend 是否扩展功能，true | false，true事件直接上抛不做默认处理
     * @param checkable 是否可选择
     */
    handleFuncAction (action, extend, checkable) {
      // 开启或者取消选择
      if (action.name === this.$default.ACTION.CHECK.name) {
        this.checkable = checkable
        // 是否可选择切换时将选中结果切换掉
        this.selectedRowKeys = []
        this.selectedRows = []
      } else {
        this.$emit('table-func-action', action, extend)
      }
    },
    // 响应清除选择
    handleFuncClearCheck () {
      this.selectedRowKeys = []
      this.$emit('table-row-selection', [], [])
    },
    // 响应操作按钮的点击
    handleRowAction (action, row, column) {
      this.$emit('table-row-action', action, row, column)
    },
    // 响应字段的选中改变
    handleColumnChange (checkedColumns) {
      this.checkedColumns = checkedColumns
    }
  }
}
</script>

<style lang="less">
.atom-table {
  .ant-card-head {
    .ant-card-head-wrapper {
      .ant-card-extra {
        padding: 12px 0;
      }
    }
  }
  .ant-card-body {
    padding-top: 4px;
    .ant-table-wrapper.center {
      overflow-x: auto;
      .ant-table {
        td {
          white-space: nowrap;
        }
        td:not(.table-action-td) {
          overflow: hidden;
          text-overflow: ellipsis;
        }
        td.table-format-progress {
          position: relative;
          padding: 0 !important;
          .atom-format-progress {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            text-align: left;
            span {
              display: inline-flex;
              align-items: center;
              height: 100%;
              padding-left: 10px;
              font-weight: bold;
            }
          }
        }
        .ant-table-thead > tr > th, .ant-table-tbody > tr > td {
          text-align: center;
        }
        .ant-table-thead > tr > th {
          position: relative;
          .atom-column-resize-bar {
            position: absolute;
            top: 0;
            bottom: 0;
            right: -3px;
            cursor: col-resize;
            width: 6px;
          }
        }
      }
    }
    .ant-table-pagination.ant-pagination {
      margin-bottom: 0;
    }
  }
}
</style>
