/**
 * form表单混入
 */
export default {
  props: {
    /**
     * 双向绑定的form表单值
     * 表单的默认值传参，默认通过fields传入
     * modelValue仅作为内外值的双向绑定
     */
    modelValue: {
      type: Object
    },
    /**
     * label 标签的文本对齐方式'left' | 'right'
     */
    labelAlign: {
      type: String,
      default: 'right'
    },
    /**
     * label 标签布局，同 <Col> 组件，设置 span offset 值
     * 如 {span: 3, offset: 12} 或 sm: {span: 3, offset: 12}
     */
    labelCol: {
      type: Object,
      default: () => {
        return { xs: 24, sm: 6, md: 6, xl: 5, xxl: 4 }
      }
    },
    /**
     * [field] 具体配置参见Field.vue
     */
    fields: {
      type: Array,
      default: null
    },
    /**
     * 表单item的大小，由上层form直接传入，同一form内各item大小一致
     * large: 40px
     * default: 32px
     * small: 24px
     */
    size: {
      type: String,
      default: 'default'
    }
  },
  data () {
    return {
      // 表单绑定的值对象
      model: {},
      // moment类型的fieldType
      dateType: ['datePicker', 'monthPicker', 'weekPicker', 'timePicker', 'rangePicker']
    }
  },
  computed: {
    // 需要为输入控件设置布局样式时，使用该属性，用法同 labelCol
    wrapperCol () {
      if (this.labelCol && this.labelCol.span) {
        return { span: 24 - this.labelCol.span }
      } else {
        return {
          xs: 24 - this.labelCol.xs || 0,
          sm: 24 - this.labelCol.sm || 0,
          md: 24 - this.labelCol.md || 0,
          xl: 24 - this.labelCol.xl || 0,
          xxl: 24 - this.labelCol.xxl || 0,
        }
      }
    }
  },
  watch: {
    // 内部form表单变化时，提交变化
    model: {
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
      },
      deep: true
    },
    // 外部传入值的改变
    modelValue: {
      handler (newValue) {
        this.model = newValue
      }
    }
  },
  created () {
    // 初始化表单数据
    this.initModel()
  },
  methods: {
    // 表单值初始化，默认值仅通过fields传入
    initModel (reset = false) {
      const model = {}
      if (this.fields) {
        this.fields.forEach(field => {
          if (this.dateType.includes(field.type)) {
            model[field.name] = this.$utils.toDate(field.default) || null
          } else {
            model[field.name] = field.default || null
          }
        })
      }
      if (reset) {
        // 重置时仅保留field中默认值
        Object.assign(this.model, model)
      } else {
        // 初始化时，保留field默认属性，以及传入的绑定对象，形成form绑定值
        Object.assign(this.model, model, this.modelValue)
      }
      this.$emit('update:modelValue', this.model)
    },
    // 表单数据提交
    submitForm () {
      this.$refs.funcForm.validate().then(() => {
        const model = {}
        // 根据fields的属性格式化date类型属性值
        this.fields.forEach(field => {
          // 如果当前值有效，返回当前值
          if (this.$utils.isValid(this.model[field.prop])) {
            if (field.type === 'datePicker') {
              model[field.prop] = this.model[field.prop].format('YYYYMMDD')
            } else if (field.type === 'monthPicker') {
              model[field.prop] = this.model[field.prop].format('YYYYMM')
            } else if (field.type === 'weekPicker') {
              model[field.prop] = this.model[field.prop].weekYear()
            } else if (field.type === 'timePicker') {
              model[field.prop] = this.model[field.prop].unix()
            } else if (field.type === 'rangePicker') {
              const mode = field.mode || 'date'
              if (mode === 'time') {
                model[field.prop] = [
                  this.model[field.prop][0].unix(),
                  this.model[field.prop][1].unix()
                ]
              } else if (mode === 'date') {
                model[field.prop] = [
                  this.model[field.prop][0].set({ hour: 0, minute: 0, second: 0, millisecond: 0 }).unix(),
                  this.model[field.prop][1].set({ hour: 23, minute: 59, second: 59, millisecond: 999 }).unix()
                ]
              } else if (mode === 'month') {
                model[field.prop] = [
                  this.model[field.prop][0].format('YYYYMM'),
                  this.model[field.prop][1].format('YYYYMM')
                ]
              } else if (mode === 'year') {
                model[field.prop] = [
                  this.model[field.prop][0].format('YYYY'),
                  this.model[field.prop][1].format('YYYY')
                ]
              } else if (mode === 'decade') {
                model[field.prop] = [
                  this.model[field.prop][0].format('YYYY'),
                  this.model[field.prop][1].format('YYYY')
                ]
              }
            } else {
              model[field.prop] = this.model[field.prop]
            }
          }
        })
        this.$emit('submit', model)
      }).catch(() => {

      })
    },
    // 重置表单
    resetForm () {
      this.$refs.funcForm.resetFields()
    },
    // 移除表单项的校验结果
    clearValidate () {
      this.$refs.funcForm.clearValidate()
    },
    // 滚动到对应字段位置
    scrollToField ({ name }) {
      this.$refs.funcForm.scrollToField(name)
    }
  }
}
