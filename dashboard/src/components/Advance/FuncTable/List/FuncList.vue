<template>
  <FormList v-bind="$attrs"
            :title="title"
            :columns="columns"
            :loadMore="loadMore"
            :hasMore="hasMore"
            :pagination="pagination"
            :dataSource="dataSource"
            :loading="loading"
            @list-filter="handleFilter"
            @list-func-action="handleFuncAction"
            @list-row-action="handleRowAction"
            @list-load-more="handleLoadMore"
            @list-row-selection="handleRowSelection"
            @list-title-link="handleTitleLink"
            @list-page-change="handlePageChange"></FormList>
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
    // 列表标题
    title: {
      type: String,
      required: false
    },
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
      default: true
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
      // 选中的行keys
      selectedRowKeys: [],
      // 选中的行
      selectedRows: [],
      // 分页信息
      pagination:  {
        current: 1,
        pageSize: 10,
        total: 0,
        showSizeChanger: true
      },
      // 分页参数
      pageParams: { curPage: 1, pageSize: 10 },
      // 过滤参数
      filterParams: {},
      // 列表loading
      loading: false,
      // 是否还有更多数据
      hasMore: true,
      // 是否数据下载
      download: false
    }
  },
  watch: {
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
  emits: ['list-func-action', 'list-row-action', 'list-load-more', 'list-row-selection', 'list-title-link', 'list-data-load'],
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
            this.$emit('list-data-load', response)
            if (this.pagination) {
              this.pagination.total = (response && response.page && response.page.totalCnt) || 0
              this.dataSource = response ? response.data : []
            } else if (this.loadMore) {
              this.hasMore = response && response.page && response.page.hasMore || false
              this.dataSource.push(...(response ? response.data : []))
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
    handleFuncAction (action) {
      if (action.extend) {
        this.$emit('list-func-action', action)
      } else {
        if (action.name === this.$default.ACTION.DELETE.name) {
          // 批量删除动作，如果扩展动作，则直接抛出，否则按照apiUrl逻辑进行处理
          if (action.extend) {
            this.$emit('list-func-action', action)
          } else {
            // apiUrl必须存在且格式合规
            if (this.$utils.isValid(action.apiUrl)) {
              // 执行删除动作，服务端需支持批删除
              this.$http.delete(action.apiUrl, { data: { ids: this.selectedRowKeys } }).then(() => {
                this.$message.success('数据删除成功！')
                this.loadTableData()
              })
            } else {
              this.$message.error('删除功能action未配置apiUrl')
            }
          }
        } else if (action.name === this.$default.ACTION.REFRESH.name) {
          // 刷新表格
          this.loadTableData().then(() => {
            this.$message.success('数据刷新成功！')
          })
        } else if (action.name === this.$default.ACTION.DOWNLOAD.name) {
          // 下载数据
          this.download = true
          this.loadTableData().then(() => {
            // 每次执法行把download置成false
            this.download = false
          })
        }
      }
    },
    // 响应加载更多
    handleLoadMore () {
      this.pageParams.curPage++
      this.loadListData()
    },
    // 响应分页切换
    handlePageChange (page, pageSize) {
      this.pageParams.curPage = page
      this.pageParams.pageSize = pageSize
      this.loadListData()
    },
    // 响应标题跳转
    handleTitleLink (row) {
      this.$emit('list-title-link', row)
    },
    // 响应行选择
    handleRowSelection (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.$emit('list-row-selection', selectedRowKeys, selectedRows)
    },
    // 响应扩展操作
    handleRowAction (action, row, fieldKeys) {
      if (action.extend) {
        this.$emit('list-row-action', action, row)
      } else {
        if (action.name === this.$default.ACTION.DELETE.name) {
          // apiUrl必须存在且格式合规
          if (this.$utils.isValid(action.apiUrl) && action.apiUrl.contains('{s}')) {
            const apiUrl = this.$utils.concatStr(action.apiUrl, row[fieldKeys.key])
            this.$http.delete(apiUrl).then(() => {
              // 提示删除成功
              this.$message.success(action.messageTitle + '删除成功！')
              this.loadTableData()
            })
          } else {
            this.$message.error('删除功能action未配置apiUrl，格式为delete/{s}')
          }
        }
      }
    }
  }
}
</script>
