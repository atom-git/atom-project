<template>
  <FormList v-bind="$attrs"
            :title="title"
            :columns="columns"
            :loadMore="loadMore"
            :hasMore="pageParams.hasMore"
            :pagination="pagination"
            :dataSource="dataSource"
            :loading="loading"
            @list-filter="handleFilter"
            @list-func-action="handleFuncAction"
            @list-row-action="handleRowAction"
            @list-row-selection="handleRowSelection"
            @list-load-more="handleLoadMore"
            @list-form-submit="handleFormSubmit"
            @list-form-cancel="handleFormCancel"></FormList>
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
      type: [String, Boolean],
      required: false
    },
    // 数据请求url
    apiUrl: {
      type: String,
      required: true
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
    // 是否展示加载更多，与pagination互斥，pagination优先级高
    loadMore: {
      type: Boolean,
      default: true
    },
    // 外部扩展的参数，有可能导致初次加载时重复请求
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
      // 分页参数
      pageParams: { curPage: 1, pageSize: 10, total: 0, hasMore: true },
      // 过滤参数
      filterParams: {},
      // 列表loading
      loading: false,
      // 是否数据下载
      download: false
    }
  },
  computed: {
    // 分页信息
    pagination () {
      if (this.loadMore) {
        return false
      } else {
        return {
          current: 1,
          pageSize: 10,
          total: this.pageParams.total,
          showSizeChanger: true,
          onChange: (page, pageSize) => {
            this.pageParams.current = page
            this.pageParams.pageSize = pageSize
          }
        }
      }
    }
  },
  watch: {
    // 监听到请求的变化变重新加载数据
    apiUrl () {
      this.loadListData(true)
    },
    // 监听扩展参数的变化重新加载数据
    extendParams: {
      deep: true,
      handler () {
        this.loadListData(true)
      }
    }
  },
  emits: ['list-data-load', 'list-func-action', 'list-row-action', 'list-row-selection', 'list-form-submit', 'list-form-cancel'],
  methods: {
    /**
     * 加载列表数据
     * @param refresh 是否刷新数据
     */
    loadListData (refresh = false) {
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
            // 如果是刷新数据，则清空原有数据
            if (refresh) {
              this.dataSource = []
            }
            if (this.loadMore) {
              this.pageParams.hasMore = response && response.page && response.page.hasMore || false
              this.dataSource.push(...(response ? response.data : []))
            } else {
              this.pageParams.total = (response && response.page && response.page.totalCnt) || 0
              this.dataSource = response ? response.data : []
            }
          }
          resolve(response)
        }).catch(error => { console.log(error) }).finally(() => { this.loading = false })
      })
    },
    // 响应表格过滤
    handleFilter (filterParams) {
      this.filterParams = filterParams
      this.loadListData(true)
    },
    // 响应加载更多
    handleLoadMore () {
      this.pageParams.curPage++
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
                this.loadListData(true)
              })
            } else {
              this.$message.error('删除功能action未配置apiUrl')
            }
          }
        } else if (action.name === this.$default.ACTION.REFRESH.name) {
          this.loadListData(true).then(() => {
            this.$message.success('数据刷新成功！')
          })
        } else if (action.name === this.$default.ACTION.DOWNLOAD.name) {
          // 下载数据
          this.download = true
          this.loadListData().then(() => {
            // 每次执法行把download置成false
            this.download = false
          })
        }
      }
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
              this.loadTableData(true)
            })
          } else {
            this.$message.error('删除功能action未配置apiUrl，格式为delete/{s}')
          }
        }
      }
    },
    // 响应行选择
    handleRowSelection (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.$emit('list-row-selection', selectedRowKeys, selectedRows)
    },
    // 响应表单提交
    handleFormSubmit (action, model, onFinish) {
      // apiUrl必须存在且格式合规
      if (this.$utils.isValid(action.apiUrl)) {
        this.$http.put(action.apiUrl, model).then(() => {
          this.$message.success('数据'.concat(action.name === this.$default.ACTION.ADD.name ? this.$default.ACTION.ADD.title : this.$default.ACTION.EDIT.title, '成功！'))
          // 重新加载数据
          this.loadListData(true)
          this.$emit('list-form-submit', action, model)
          onFinish(true)
        }).catch(error => {
          onFinish(false, error)
        })
      } else {
        this.$message.error('编辑功能action必须配置apiUrl')
      }
    },
    // 响应表单取消
    handleFormCancel (action) {
      this.$emit('list-form-cancel', action)
    },
    // 刷新数据
    refresh () {
      this.loadListData(true)
    }
  }
}
</script>
