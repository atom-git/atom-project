<template>
  <a-card :class="['atom-tab-table', tabPosition ]"
          :bordered="false"
          :tabList="tabList"
          :activeTabKey="activeTab"
          @tabChange="handleTabChange">
    <!-- 标题 -->
    <template #tabBarExtraContent>
      <FuncTitle :title="title"></FuncTitle>
    </template>
    <FuncTable v-bind="tableAttrs"
               :title="false"
               :apiUrl="tableAttrs.apiUrl"
               :columns="tableAttrs.columns"
               :extendParams="extendParams"
               @table-data-load="handleDataLoad"
               @table-func-action="handleFuncAction"
               @table-row-action="handleRowAction"
               @table-row-selection="handleRowSelection"
               @table-form-submit="handleFormSubmit"
               @table-form-cancel="handleFormCancel">
      <!-- 把外部传入的form slot传入内部 -->
      <template v-for="slotName in Object.keys($slots)" #[slotName]="props">
        <slot :name="slotName" v-bind="props"></slot>
      </template>
    </FuncTable>
  </a-card>
</template>

<script>
/**
 * tabs: [{ tab: {tab相关配置}, table: {FuncTable参数合集} }]
 * tab key值不需要指定，默认采用index作为key值
 */
import FuncTitle from '@/components/Common/FuncTitle'
import FuncTable from './FuncTable'
export default {
  name: 'TabTable',
  components: { FuncTitle, FuncTable },
  props: {
    // 多tabs标题
    title: {
      type: String,
      required: false
    },
    // 多标签表格的数据集合
    tabs: {
      type: Array,
      required: true
    },
    // 扩展的参数，用于tab切换时有需要的增加参数
    extendParams: {
      type: Object,
      default: () => ({})
    },
    // tab标签默认位置
    tabPosition: {
      type: String,
      default: 'right'
    }
  },
  data () {
    return {
      // tab列表，当前激活的tab
      activeTab: '0'
    }
  },
  computed: {
    // tab列表，通过内部处理隐藏key的输入，默认采用index作为key
    tabList () {
      return this.tabs.map((tab, index) => ({ ...tab, key: index + '' }))
    },
    // 当前选中的tab配置信息
    tableAttrs () {
      return this.tabList.filter(tab => tab.key === this.activeTab)[0].table
    }
  },
  emits: ['table-tab-change', 'table-data-load', 'table-func-action', 'table-row-action', 'table-row-selection', 'table-form-submit', 'table-form-cancel'],
  methods: {
    // 响应tab切换
    handleTabChange (activeTab) {
      this.activeTab = activeTab
      this.$emit('table-tab-change', activeTab, this.tabs[this.activeTab])
    },
    // 响应功能区域操作按钮
    handleFuncAction (action) {
      this.$emit('table-func-action', action, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应行级操作按钮
    handleRowAction (action, row, column) {
      this.$emit('table-row-action', action, row, column, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应行选中
    handleRowSelection (selectedRowKeys, selectedRows) {
      this.$emit('table-row-selection', selectedRowKeys, selectedRows, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应form表单的提交
    handleFormSubmit (action, model) {
      this.$emit('table-form-submit', action, model, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应form表单的取消
    handleFormCancel (action) {
      this.$emit('table-form-cancel', action, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应数据加载
    handleDataLoad (response) {
      this.$emit('table-data-load', response, this.activeTab, this.tabs[this.activeTab])
    }
  }
}
</script>

<style lang="less">
.atom-tab-table {
  &.right {
    .ant-tabs-top-bar {
      margin-bottom: -16px;
      text-align: right;
      .ant-tabs-extra-content {
        float: left !important;
        font-weight: 500;
        font-size: 16px;
      }
    }
  }
  .ant-tabs-tabpane {
    .atom-table {
      margin-top: 0;
      .ant-card-head {
        padding: 0;
      }
    }
    .atom-form, .atom-table {
      .ant-card-body {
        padding-left: 0;
        padding-right: 0;
      }
    }
  }
}
</style>
