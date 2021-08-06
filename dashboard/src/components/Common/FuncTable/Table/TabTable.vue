<template>
  <a-tabs v-bind="$attrs"
          class="atom-table-tab"
          v-model:activeKey="activeKey"
          @change="handleTabChange">
    <a-tab-pane v-for="(item, index) in tabs"
                :forceRender="(item.tab && item.tab.forceRender) || false"
                :key="index"
                :tab="item.tab.title">
      <FuncTable v-bind="item.table"
                 :apiUrl="item.table.apiUrl"
                 :columns="item.table.columns"
                 @table-func-action="handleFuncAction"
                 @table-row-action="handleRowAction"
                 @table-form-submit="handleFormSubmit"
                 @table-form-cancel="handleFormCancel"
                 @table-data-load="handleDataLoad">
        <!-- 把外部传入的form slot传入内部 -->
        <template v-for="slotName in Object.keys($slots)" #[slotName]="props">
          <slot :name="slotName" v-bind="props"></slot>
        </template>
      </FuncTable>
    </a-tab-pane>
  </a-tabs>
</template>

<script>
/**
 * tabs: [{ tab: {tab相关配置}, table: {FuncTable参数合集} }]
 * tab key值不需要指定，默认采用index作为key值
 * tab标签值采用title字段设置或者tab挂载点注入
 */
import FuncTable from './FuncTable'
export default {
  name: 'TabTable',
  components: { FuncTable },
  props: {
    // 多标签表格的数据集合
    tabs: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      // 当前激活的table
      activeKey: 0
    }
  },
  emits: ['table-tab-change', 'table-data-load', 'table-func-action', 'table-row-action', 'table-form-submit', 'table-form-cancel'],
  methods: {
    // 响应tab切换
    handleTabChange (activeKey) {
      this.$emit('table-tab-change', activeKey, this.tabs[activeKey])
    },
    // 响应功能区域操作按钮
    handleFuncAction (action) {
      this.$emit('table-func-action', action, this.activeKey, this.tabs[this.activeKey])
    },
    // 响应行级操作按钮
    handleRowAction (action, row, column) {
      this.$emit('table-row-action', action, row, column, this.activeKey, this.tabs[this.activeKey])
    },
    // 响应form表单的提交
    handleFormSubmit (action, model) {
      this.$emit('table-form-submit', action, model, this.activeKey, this.tabs[this.activeKey])
    },
    // 响应form表单的取消
    handleFormCancel (action) {
      this.$emit('table-form-cancel', action, this.activeKey, this.tabs[this.activeKey])
    },
    // 响应数据加载
    handleDataLoad (response) {
      this.$emit('table-data-load', response, this.activeKey, this.tabs[this.activeKey])
    }
  }
}
</script>

<style lang="less">
.atom-table-tab {
  .ant-tabs-bar {
    margin-bottom: -16px;
    text-align: right;
  }
}
</style>
