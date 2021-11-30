/**
 * FormTable的数据编辑混入
 * 当前编辑的表单的信息，包括新增或者编辑两种表单的form表单
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
  data () {
    return {
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
  emits: ['table-form-submit', 'table-form-cancel'],
  methods: {
    // 根据column构建dataForm，在点击时才生成，存在add，edit属性时才生成
    generateUpdateForm (column) {
      // 为新增时
      if (column.form.add) {
        const addField = {}
        this.generateColumnForm(column, column.form, column.form.add, addField)
        this.addFields.push(addField)
        if (this.$utils.isValid(addField.default)) {
          this.defaultModel[addField.name] = addField.default
        }
        if (addField.slot) {
          this.addSlots.push(addField.slot)
        }
      }
      // 为编辑时
      if (column.form.edit) {
        const editField = {}
        this.generateColumnForm(column, column.form, column.form.edit, editField)
        this.editFields.push(editField)
        if (editField.slot) {
          this.editSlots.push(editField.slot)
        }
      }
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
      this.$emit('table-form-submit', this.curAction, model, onFinish)
    },
    // 响应form表单的取消
    handleFormCancel () {
      this.formModel = {}
      this.formVisible = false
      this.formError = undefined
      this.$emit('table-form-cancel', this.curAction)
    }
  }
}
