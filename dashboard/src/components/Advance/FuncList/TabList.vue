<template>
  <a-card :class="['atom-tab-list', tabPosition ]"
          :bordered="false"
          :tabList="tabList"
          :activeTabKey="activeTab"
          @tabChange="handleTabChange">
    <!-- 标题 -->
    <template v-if="title" #tabBarExtraContent>
      <FuncTitle :title="title"></FuncTitle>
    </template>
    <FuncList v-bind="listAttrs"
               :title="false"
               :apiUrl="listAttrs.apiUrl"
               :columns="listAttrs.columns"
               :extendParams="extendParams"
               @list-data-load="handleDataLoad"
               @list-func-action="handleFuncAction"
               @list-row-action="handleRowAction"
               @list-row-selection="handleRowSelection"
               @list-form-submit="handleFormSubmit"
               @list-form-cancel="handleFormCancel">
    </FuncList>
  </a-card>
</template>

<script>
/**
 * tabs: [tab: '标签名称', list: {FuncList参数合集} }]
 * tab key值不需要指定，默认采用index作为key值
 */
import FuncTitle from '@/components/Common/FuncTitle'
import FuncList from './FuncList'
export default {
  name: 'TabList',
  components: { FuncTitle, FuncList },
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
      return this.tabs.map((tab, index) => {
        if (index === 0) {
          this.activeTab = (tab.key || index) + ''
        }
        return { ...tab, key: (tab.key || index) + '' }
      })
    },
    // 当前选中的tab配置信息
    listAttrs () {
      return this.tabList.filter(tab => (tab.key + '') === this.activeTab)[0].list
    }
  },
  emits: ['list-tab-change', 'list-data-load', 'list-func-action', 'list-row-action', 'list-row-selection', 'list-form-submit', 'list-form-cancel'],
  methods: {
    // 响应tab切换
    handleTabChange (activeTab) {
      this.activeTab = activeTab
      this.$emit('list-tab-change', activeTab, this.tabs[this.activeTab])
    },
    // 响应功能区域操作按钮
    handleFuncAction (action) {
      this.$emit('list-func-action', action, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应行级操作按钮
    handleRowAction (action, row, column) {
      this.$emit('list-row-action', action, row, column, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应行选中
    handleRowSelection (selectedRowKeys, selectedRows) {
      this.$emit('list-row-selection', selectedRowKeys, selectedRows, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应form表单的提交
    handleFormSubmit (action, model) {
      this.$emit('list-form-submit', action, model, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应form表单的取消
    handleFormCancel (action) {
      this.$emit('list-form-cancel', action, this.activeTab, this.tabs[this.activeTab])
    },
    // 响应数据加载
    handleDataLoad (response) {
      this.$emit('list-data-load', response, this.activeTab, this.tabs[this.activeTab])
    }
  }
}
</script>

<style lang="less">
.atom-tab-list {
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
    .atom-list {
      margin-top: 0;
      .ant-card-head {
        padding: 0;
      }
    }
    .atom-form, .atom-list {
      .ant-card-body {
        padding-left: 0;
        padding-right: 0;
      }
    }
  }
}
</style>
