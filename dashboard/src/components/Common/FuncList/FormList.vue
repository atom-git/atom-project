<template>
  <FormatList v-bind="$attrs"
              :fieldKeys="fieldKeys"
              :itemTitleFormat="itemTitleFormat"
              :dataSource="dataSource"
              @list-func-action="handleFuncAction"
              @list-load-more="handleLoadMore"
              @list-title-link="handleTitleLink"
              @list-row-selection="handleRowSelection"
              @list-row-action="handleRowAction"></FormatList>
</template>

<script>
/**
 * 带表单的格式化列表
 */
import FormatList from './FormatList'
export default {
  name: 'FormList',
  components: { FormatList },
  props: {
    /**
     * 字段列表 { title, dataIndex, span, class, format, form }
     * title: 表示对应的FormatList中fieldKeys被替换的key
     * key为title时其format目前仅支持formatBadge，其他字段暂不支持format
     * dataIndex: 表示字段属性名
     *
     * 其他与FuncTable中完全一致
     */
    columns: {
      type: Array,
      required: true
    },
    // 数据列表
    dataSource: {
      type: Array,
      required: false
    }
  },
  data () {
    return {
      // 字段key值对象
      fieldKeys: {},
      // 列表中标题字段格式化 formatBadge
      itemTitleFormat: {},
      // 编辑字段列表
      fields: []
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
  methods: {
    // 初始化List属性
    initList (columns) {
      columns.forEach(column => {
        // key存在时才放到fieldKeys中
        if (column.key) {
          this.fieldKeys[column.key] = column.dataIndex
        }
        /**
         * 由于List其字段属性相对固定
         * 通过遍历自动生成大部分表单属性
         */
        const formProps = Object.assign({ title: column.title, name: column.dataIndex }, column.form)
        if (column.key === 'title') {
          this.itemTitleFormat = column.format
          this.fields.push({ type: 'input', rules: [{ required: true }], ...formProps })
        } else if (column.key === 'avatar') {
          this.fields.push({ type: 'imagePicker', height: 60, maxSize: 200, rules: [{ required: true }], ...formProps })
        } else if (column.key === 'description') {
          this.fields.push({ type: 'textarea', rows: 2, maxlength: 100, rules: [{ required: true }], ...formProps })
        } else if (column.key === 'content') {
          this.fields.push({ type: 'textarea', rows: 3, maxlength: 100, rules: [{ required: true }], ...formProps })
        } else if (column.key === 'extra') {
          this.fields.push({ type: 'imagePicker', height: 160, maxSize: 200, rules: [{ required: true }], ...formProps })
        } else {
          // 其他字段生成
          this.fields.push(formProps)
        }
      })
      // 如果没有配置其key字段，则默认采用id作为key
      if (!this.fieldKeys.key) {
        this.fieldKeys.key = 'id'
      }
    },
    // 响应功能区域操作
    handleFuncAction (action, extend) {
      console.log(action, extend)
    },
    // 响应加载更多
    handleLoadMore () {
      console.log('load more')
    },
    // 响应标题跳转
    handleTitleLink (item) {
      console.log(item)
    },
    // 响应行选择
    handleRowSelection (selectedRowKeys, selectedRows) {
      console.log(selectedRowKeys, selectedRows)
    },
    // 响应扩展操作
    handleRowAction (action, row) {
      console.log(action, row)
    }
  }
}
</script>
