/**
 * form表单混入
 */
import FieldRules from '@/components/Common/Render/FieldRules'
export default {
  props: {
    /**
     * 双向绑定的form表单值
     * 表单的默认值传参，默认通过fields传入
     * modelValue仅作为内外值的双向绑定
     */
    modelValue: {
      type: Object,
      default: () => ({})
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
        return { xs: 24, sm: 6, md: 8, lg: 8, xl: 6, xxl: 4 }
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
    // 需要为输入控件设置布局样式时，使用该属性，用法同 labelCol,需要兼容处理多种情况
    wrapperCol () {
      if (this.labelCol && this.labelCol.span) {
        return { span: 24 - this.labelCol.span }
      } else if (this.labelCol && this.labelCol.style) {
        return { span: 24 }
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
    // 监听fields变化
    fields: {
      deep: true,
      handler (newValue) {
        // 初始化表单数据
        this.initModel(this.$utils.deepClone(newValue))
      }
    },
    // 外部传入值的改变
    modelValue: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.model = newValue
      }
    },
    // 内部form表单变化时，提交变化
    model: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
        this.$emit('change', newValue)
      }
    }
  },
  created () {
    // 初始化表单数据
    this.initModel(this.$utils.deepClone(this.fields))
  },
  emits: ['update:modelValue', 'change', 'submit', 'reset'],
  methods: {
    // 表单值初始化，默认值仅通过fields传入
    initModel (fields) {
      const model = {}
      if (this.$utils.isValid(fields)) {
        fields.forEach(field => {
          this.formatModel(field, model)
        })
      }
      this.model = Object.assign(model, this.modelValue)
    },
    // 格式化双绑值
    formatModel (field, model) {
      if (this.dateType.includes(field.type)) {
        model[field.name] = this.$utils.toDate(field.default) || undefined
      } else if ('inputGroup' === field.type) {
        // 对子集进行拆解后写入值
        field.group.forEach(groupField => {
          this.formatModel(groupField, model)
        })
      } else {
        model[field.name] = field.default
      }
    },
    // 表单数据提交
    submitForm () {
      this.$refs.funcForm.validate().then(() => {
        const model = {}
        // 根据fields的属性格式化date类型属性值
        this.fields.forEach(field => {
          // 如果当前值有效，返回当前值
          if (this.$utils.isValid(this.model[field.name])) {
            this.formatValue(field, model)
          } else if (field.type === 'inputGroup') {
            this.formatValue(field, model)
          }
        })
        // 与this.modelValue叠加是为了防止外部传入的id等值
        this.$emit('submit', Object.assign({}, this.modelValue, model))
      }).catch(error => {
        console.log(error)
      })
    },
    // 格式化field Value值，用于传输
    formatValue (field, model) {
      if (field.type === 'datePicker') {
        if (field.showTime) {
          model[field.name] = this.model[field.name].format(field.format || 'YYYY-MM-DD HH:mm:ss')
        } else {
          model[field.name] = this.model[field.name].format(field.format || 'YYYYMMDD')
        }
      } else if (field.type === 'monthPicker') {
        model[field.name] = this.model[field.name].format('YYYYMM')
      } else if (field.type === 'weekPicker') {
        model[field.name] = this.model[field.name].weekYear()
      } else if (field.type === 'timePicker') {
        model[field.name] = this.model[field.name].unix()
      } else if (field.type === 'rangePicker') {
        const mode = field.mode || 'date'
        if (mode === 'time') {
          model[field.name] = [
            this.model[field.name][0].unix(),
            this.model[field.name][1].unix()
          ]
        } else if (mode === 'date') {
          if (field.showTime) {
            model[field.name] = [
              this.model[field.name][0].unix(),
              this.model[field.name][1].unix()
            ]
          } else {
            model[field.name] = [
              this.model[field.name][0].set({ hour: 0, minute: 0, second: 0, millisecond: 0 }).unix(),
              this.model[field.name][1].set({ hour: 23, minute: 59, second: 59, millisecond: 999 }).unix()
            ]
          }
        } else if (mode === 'month') {
          model[field.name] = [
            this.model[field.name][0].format('YYYYMM'),
            this.model[field.name][1].format('YYYYMM')
          ]
        } else if (mode === 'year') {
          model[field.name] = [
            this.model[field.name][0].format('YYYY'),
            this.model[field.name][1].format('YYYY')
          ]
        } else if (mode === 'decade') {
          model[field.name] = [
            this.model[field.name][0].format('YYYY'),
            this.model[field.name][1].format('YYYY')
          ]
        }
      } else {
        // 判断是否为inputGroup
        if (field.type === 'inputGroup') {
          model[field.name] = {}
          field.group.forEach(groupField => {
            this.formatModel(groupField, model)
            if (this.$utils.isValid(this.model[groupField.name])) {
              model[field.name][groupField.name] = this.model[groupField.name]
            }
          })
        } else if (field.type === 'fileUpload') {
          model[field.name] = [...this.model[field.name]].map(file => file.key)
        } else {
          model[field.name] = this.model[field.name]
        }
      }
    },
    // 初始化校验规则
    initRules (field) {
      const rules = field.rules
      // TODO 如果inputGroup时需要单独处理内部rules
      // 为一个有效的数据时，即String, Object的混合数组
      if (this.$utils.isValid(rules)) {
        return rules.map(rule => {
          // 外部message优先级最高，如果没有则按照内置校验规则生成相应的message
          if (!rule.message) {
            FieldRules.resolve(field, rule)
          }
          return rule
        })
      } else {
        return []
      }
    },
    // 重置表单
    resetForm () {
      this.$refs.funcForm.resetFields()
      this.$emit('reset', this.model)
    },
    // 暴露validate方法
    validate () {
      return this.$refs.funcForm.validate()
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
