/**
 * FormList数据编辑混入
 * 当前编辑的表单的信息，包括新增或者编辑两种表单的form表单
 * formMode: 弹框类型：modal, drawer
 * formAttrs: 弹出框的属性
 * formType: add | edit
 * formModel: 表单绑定的属性值
 * addFields: 新增字段列表
 * addSlots: 新增表单的slots
 * editFields: 编辑字段列表
 * editSlots: 编辑表单的slots
 * formVisible: form的显隐
 * formLoading: form的loading
 * 其他form表单配置项见FormFilter
 */
export default {
  props: {
    // form表单的宽度
    formWidth: {
      type: Number,
      defalut: 720
    },
    // form表单的label,wrapper宽度分配
    labelCol: {
      type: Object,
      default: () => ({ xs: 24, sm: 6, md: 8, lg: 8, xl: 6, xxl: 5 })
    }
  },
  data () {
    return {
      // 过滤表单项 filterFields: 过滤字段
      filterFields: [],
      // 过滤器内部绑定的值对象
      filterModel: {},
      // 过滤器挂载slot
      filterSlots: [],
      // 表单处理类型
      formType: 'add',
      // 新增默认的表单值
      defaultModel: {},
      // 编辑form表单的值
      formModel: {},
      // 新增表单字段
      addFields: [],
      // 编辑表单字段
      editFields: [],
      // 新增表单的slots
      addSlots: [],
      // 编辑表单的slots
      editSlots: []
    }
  },
  computed: {
    // form表单的fields
    formFields () {
      return this.formType === 'add' ? this.addFields : this.editFields
    },
    // form表单的slots
    formSlots () {
      return this.formType === 'add' ? this.addSlots : this.editSlots
    }
  },
  emits: ['list-filter', 'list-form-submit', 'list-form-cancel'],
  mounted () {
    // 在FormList挂载时提交一次filter数据，好让FuncList中能够正常的进行数据初始化
    this.handleFilterSubmit(this.filterModel)
  },
  methods: {
    /**
     * filter过滤器配置
     */
    // 根据column构建FilterForm，存在filter属性时才生成
    generateFilterForm (column) {

    },
    // 响应filter form的提交
    handleFilterSubmit (filterModel) {
      this.$emit('list-filter', filterModel)
    },
    // 响应filter form的重置
    handleFilterReset (filterModel) {
      this.$emit('list-filter', filterModel)
    },
    /**
     * form表单配置
     */
    // 根据column构建dataForm，在点击时才生成，存在add，edit属性时才生成
    generateUpdateForm (column) {

    },
    // 响应form表单的提交
    handleFormSubmit (model) {
      this.formLoading = true
      this.formError = undefined
      const onFinish = (status = true, error) => {
        this.formLoading = false
        this.formVisible = !status
        this.formError = error ? error.errorMsg : undefined
      }
      this.$emit('list-form-submit', this.curAction, model, onFinish)
    },
    // 响应form表单的取消
    handleFormCancel () {
      this.formModel = {}
      this.formVisible = false
      this.formError = undefined
      this.$emit('list-form-cancel', this.curAction)
    }
  }
}
