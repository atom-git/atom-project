<template>
  <FormTable ref="formTable"
             v-bind="$attrs"
             :columns="columns"
             :dataSource="dataSource"
             :pagination="tablePagination"
             :loading="loading"
             @table-filter="handleFilter"
             @table-func-action="handleFuncAction"
             @table-row-action="handleRowAction"
             @table-form-submit="handleFormSubmit"
             @table-form-cancel="handleFormCancel"
             @table-change="handleTableChange">
    <!-- 把外部传入的form slot传入内部 -->
    <template v-for="slotName in Object.keys($slots)" #[slotName]="props">
      <slot :name="slotName" v-bind="props"></slot>
    </template>
  </FormTable>
</template>

<script>
import FormTable from './FormTable'
export default {
  name: 'FuncTable',
  components: { FormTable },
  props: {
    // 数据请求url
    apiUrl: {
      type: String,
      required: true
    },
    // 参考FormTable的配置
    columns: {
      type: Array,
      required: true
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
      // 表格loading
      loading: false,
      // 分页信息
      tablePagination: this.pagination,
      // 表格数据
      dataSource: [],
      // 过滤参数
      filterParams: {},
      // 排序参数
      orderData: {},
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
      handler (newValue) {
        this.tablePagination = newValue
      },
      deep: true
    },
    // 监听到请求的变化变重新加载数据
    apiUrl () {
      this.loadTableData()
    },
    // 监听扩展参数的变化重新加载数据
    extendParams: {
      handler () {
        this.loadTableData()
      },
      deep: true
    }
  },
  emits: ['table-data-load', 'table-func-action', 'table-row-action', 'table-form-submit', 'table-form-cancel'],
  methods: {
    // 加载数据
    loadTableData () {
      return new Promise(resolve => {
        if (!this.$utils.isValid(this.apiUrl)) {
          console.warn('请提供有效的apiUrl')
          return
        }
        this.loading = true
        const config = {
          params: Object.assign({}, this.pageParams, this.filterParams, {
            orderData: this.orderData,
            download: this.download
          }, this.extendParams)
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
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 响应表格过滤
    handleFilter (filterParams) {
      this.filterParams = filterParams
      this.loadTableData()
    },
    // 响应pagination, sorter改变
    handleTableChange (pagination, filters, sorter) {
      // 分页信息改变
      if (pagination && pagination.current) {
        this.tablePagination = pagination
      }
      if (sorter && sorter.column) {
        this.orderData = {
          orderColumn: sorter.column.dataIndex,
          orderType: {ascend: 'asc', descend: 'desc'}[sorter.order]
        }
      }
      this.loadTableData()
    },
    // 响应功能区域操作按钮
    handleFuncAction (action) {
      if (action.extend) {
        this.$emit('table-func-action', action)
      } else {
        if (action.name === this.$default.ACTION_REFRESH.name) {
          // 刷新表格
          this.loadTableData().then(() => {
            this.$message.success('数据刷新成功！')
          })
        } else if (action.name === this.$default.ACTION_DOWNLOAD.name) {
          // 下载数据
          this.download = true
          this.loadTableData().then(() => {
            // 每次执法行把download置成false
            this.download = false
          })
        }
      }
    },
    // 响应行级操作按钮
    handleRowAction (action, row, column) {
      if (action.extend) {
        this.$emit('table-row-action', action, row, column)
      } else {
        if (action.name === this.$default.ACTION_DELETE.name) {
          const apiUrl = this.$utils.concatStr(action.apiUrl, row[action.replaceFields.id])
          this.$http.delete(apiUrl).then(() => {
            // 提示删除成功
            this.$message.success(action.messageTitle + '删除成功！')
            this.loadTableData()
          })
        }
      }
    },
    // 响应form表单的提交
    handleFormSubmit (action, model, onFinish) {
      this.$http.post(action.apiUrl, model).then(() => {
        this.$message.success('数据'.concat(action.name === this.$default.ACTION_ADD.name ? this.$default.ACTION_ADD.title : this.$default.ACTION_EDIT.title, '成功！'))
        // 重新加载数据
        this.loadTableData()
        this.$emit('table-form-submit', action, model)
        onFinish(true)
      }).catch(error => {
        onFinish(false, error)
      })
    },
    // 响应form表单的取消
    handleFormCancel (action) {
      this.$emit('table-form-cancel', action)
    },
    // 刷新数据
    refresh () {
      this.loadTableData()
    }
  },
  mounted () {
    // 挂载时加载数据，保障过滤器已经初始化完成
    this.loadTableData()
  }
}
</script>
