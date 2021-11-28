<template>
  <FormList v-bind="$attrs"
              :columns="columns"
              :loadMore="loadMore"
              :pagination="listPagination"
              :dataSource="dataSource"
              :loading="loading"
              @list-func-action="handleFuncAction"
              @list-load-more="handleLoadMore"
              @list-title-link="handleTitleLink"
              @list-row-selection="handleRowSelection"
              @list-row-action="handleRowAction"></FormList>
</template>

<script>
/**
 * 带请求交互的格式化列表
 */
import FormList from './FormList'
export default {
  name: 'FuncList',
  components: { FormList },
  props: {
    // 数据请求url
    apiUrl: {
      type: String,
      required: true
    },
    // 参考FormList的配置
    columns: {
      type: Array,
      required: true
    },
    // 是否展示加载更多，与pagination互斥，pagination优先级高
    loadMore: {
      type: Boolean,
      default: false
    },
    // 分页信息
    pagination: {
      type: [Boolean, Object],
      default: () => {
        return {
          current: 1,
          pageSize: 10,
          total: 0,
          showSizeChanger: true
        }
      }
    },
    // 外部扩展的参数
    extendParams: {
      type: Object,
      default: () => ({})
    }
  },
  data () {
    return {
      // 数据列表
      dataSource: [],
      // 列表分页信息
      listPagination: this.pagination,
      // 选中的行keys
      selectedRowKeys: [],
      // 选中的行
      selectedRows: [],
      // 过滤参数
      filterParams: {},
      // 列表loading
      loading: false,
      // 是否数据下载
      download: false
    }
  },
  computed: {
    // 分页参数
    pageParams () {
      return this.tablePagination === false ? {} : {
        curPage: this.tablePagination.current,
        pageSize: this.tablePagination.pageSize
      }
    }
  },
  watch: {
    // 监听分页信息的变化重新加载数据
    pagination: {
      deep: true,
      handler (newValue) {
        this.listPagination = newValue
      }
    },
    // 监听到请求的变化变重新加载数据
    apiUrl () {
      this.loadListData()
    },
    // 监听扩展参数的变化重新加载数据
    extendParams: {
      deep: true,
      handler () {
        this.loadListData()
      }
    }
  },
  methods: {
    // 加载列表数据
    loadListData () {
      return new Promise(resolve => {
        if (!this.$utils.isValid(this.apiUrl)) {
          console.warn('请提供有效的apiUrl')
          return
        }
        this.loading = true
        const config = {
          params: Object.assign({}, this.pageParams, this.filterParams, { download: this.download }, this.extendParams)
        }
        // 如果是下载操作需要配置responseType为blob
        if (this.download) {
          config.responseType = 'blob'
        }
        this.$http.get(this.apiUrl, config).then(response => {
          if (this.download) {
            // 对下载的请求单独处理
            this.$utils.download(response)
          } else {
            this.$emit('table-data-load', response)
            if (this.tablePagination) {
              this.tablePagination.total = response && response.page ? response.page.totalCnt : 0
              this.dataSource = response ? response.data : []
            } else {
              this.dataSource = response
            }
          }
          resolve(response)
        }).finally(() => { this.loading = false })
      })
    },
    // 响应表格过滤
    handleFilter (filterParams) {
      this.filterParams = filterParams
      this.loadListData()
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
