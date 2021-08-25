/**
 * FormTable的过滤器混入
 */
export default {
  data () {
    return {
      // 过滤表单项 filterFields: 过滤字段
      filterFields: [],
      // 过滤器内部绑定的值对象
      filterModel: {},
      // 过滤器挂载slot
      filterSlots: []
    }
  },
  emits: ['table-filter'],
  mounted () {
    // 在formTable挂载时提交一次filter数据，好让FuncTable中能够正常的进行数据初始化
    this.handleFilterSubmit(this.filterModel)
  },
  methods: {
    // 根据column构建filterForm，存在filter属性时才生成
    generateFilterForm (column) {
      const filterField = {}
      // 如果是对象，则按照对象的方式来处理，否则取默认的情况，属性优先级是按照容错处理>filter>column
      this.generateColumnForm(column, column.form, column.form.filter, filterField)
      this.filterFields.push(filterField)
      if (filterField.slot) {
        this.filterSlots.push(filterField.slot)
      }
    },
    // 响应filter form的提交
    handleFilterSubmit (filterModel) {
      this.$emit('table-filter', filterModel)
    },
    // 响应filter form的重置
    handleFilterReset (filterModel) {
      this.$emit('table-filter', filterModel)
    }
  }
}
