<template>
  <!-- 基础组件 -->
  <!-- input -->
  <a-input v-if="isType()"
           v-bind="renderField"
           :value="modelValue"
           @change="handleInputChange" allowClear/>
  <!-- textarea -->
  <a-textarea v-else-if="isType('textarea')"
              v-bind="renderField"
              :value="modelValue"
              @change="handleInputChange" allowClear/>
  <!-- number，这里属性只能单独写，直接用v-bind会存在解析异常 -->
  <a-input-number v-else-if="isType('number')"
                  :value="modelValue"
                  :formatter="renderField.formatter"
                  :parser="renderField.parser"
                  :precision="renderField.precision"
                  :autofocus="renderField.autofocus"
                  :disabled="renderField.disabled"
                  :max="renderField.max"
                  :min="renderField.min"
                  :decimalSeparator="renderField.decimalSeparator"
                  :size="renderField.size"
                  :step="renderField.step"
                  :style="renderField.style"
                  @change="handleChange" allowClear/>
  <!-- select, multiple, tags 由mode来决定 -->
  <!-- tags模式下，option的value不能用number类型 -->
  <a-select v-else-if="isType('select')"
            :value="modelValue"
            @change="handleChange"
            :placeholder="renderField.placeholder"
            :mode="renderField.mode"
            :disabled="renderField.disabled"
            :showSearch="renderField.showSearch"
            :style="renderField.style"
            :filterOption="true"
            optionFilterProp="title" allowClear>
    <a-select-option v-for="option in renderField.options"
                     :value="option[renderField.replaceFields.value]"
                     :title="option[renderField.replaceFields.title]"
                     :key="option[renderField.replaceFields.value]">
        <span v-if="$utils.isValid(option[renderField.replaceFields.status]) || option[renderField.replaceFields.color]"
              role="img" :ariaLabel="option[renderField.replaceFields.title]">
          <a-badge v-if="$utils.isValid(option[renderField.replaceFields.status])"
                   :status="formatStatus(option[renderField.replaceFields.status])"
                   :text="option[renderField.replaceFields.title]"/>
          <a-badge v-else-if="option[renderField.replaceFields.color]"
                   :color="option[renderField.replaceFields.color]"
                   :text="option[renderField.replaceFields.title]"/>
        </span>
      <template v-else>{{ option[renderField.replaceFields.title] }}</template>
    </a-select-option>
  </a-select>
  <!-- remoteSelect -->
  <a-select v-else-if="isType('remoteSelect')"
            :value="modelValue"
            :placeholder="renderField.placeholder"
            :mode="renderField.mode"
            :showSearch="true"
            :style="renderField.style"
            :filterOption="false"
            :defaultActiveFirstOption="renderField.defaultActiveFirstOption || false"
            @search="handleRemoteSearch"
            @change="handleRemoteSelect"
            :disabled="renderField.disabled"
            allowClear>
    <a-select-option v-for="option in renderField.options"
                     :value="option[renderField.replaceFields.value]"
                     :title="option[renderField.replaceFields.title]"
                     :key="option[renderField.replaceFields.value]"
                     :bindData="option">
      {{ option[renderField.replaceFields.title] }}
    </a-select-option>
  </a-select>
  <!-- radio -->
  <a-radio-group v-else-if="isType('radio')"
                 :value="modelValue"
                 @change="handleRadioChange"
                 :name="renderField.name"
                 :buttonStyle="renderField.buttonStyle"
                 :disabled="renderField.disabled">
    <template v-if="renderField.mode === 'button'">
      <a-radio-button v-for="option in renderField.options"
                      :key="option[renderField.replaceFields.value]"
                      :value="option[renderField.replaceFields.value]">
        {{ option[renderField.replaceFields.title] }}
      </a-radio-button>
    </template>
    <template v-else>
      <a-radio v-for="option in renderField.options"
               :key="option[renderField.replaceFields.value]"
               :value="option[renderField.replaceFields.value]">
        {{ option[renderField.replaceFields.title] }}
      </a-radio>
    </template>
  </a-radio-group>
  <!-- switch -->
  <a-switch v-else-if="isType('switch')"
            v-bind="renderField"
            :style="{ width: 'auto' }"
            :checked="modelValue"
            :checkedValue="renderField.checkedValue || 1"
            :unCheckedValue="renderField.unCheckedValue || 0"
            @change="handleChange"/>
  <!-- cascader TODO 分级loadData -->
  <a-cascader v-else-if="isType('cascader')"
              v-bind="renderField"
              :fieldNames="renderField.replaceFields"
              :value="modelValue"
              @change="handleChange" allowClear/>
  <!-- checkbox -->
  <a-checkbox-group v-else-if="isType('checkbox')" v-bind="renderField" :value="modelValue" @change="handleChange"/>

  <!-- 时间组件 -->
  <!-- datePicker -->
  <a-date-picker v-else-if="isType('datePicker')" :value="modelValue"
                 v-bind="renderField"
                 @change="handleChange"
                 :format="renderField.format || 'YYYY-MM-DD'" allowClear></a-date-picker>
  <!-- monthPicker -->
  <a-month-picker v-else-if="isType('monthPicker')" :value="modelValue"
                  @change="handleChange"
                  mode="month"
                  v-bind="renderField"
                  :format="renderField.format || 'YYYY-MM'" allowClear></a-month-picker>
  <!-- rangePicker -->
  <a-range-picker v-else-if="isType('rangePicker')" :value="modelValue"
                  v-bind="renderField"
                  @change="handleChange"
                  :mode="initMode('rangePicker', renderField)"
                  :format="renderField.format || 'YYYY-MM-DD'" allowClear></a-range-picker>
  <!-- weekPicker -->
  <a-week-picker v-else-if="isType('weekPicker')" :value="modelValue"
                 v-bind="renderField"
                 @change="handleChange"
                 :format="renderField.format || 'YYYY-WW'" allowClear></a-week-picker>
  <!-- timePicker -->
  <a-time-picker v-else-if="isType('timePicker')" :value="modelValue"
                 v-bind="renderField"
                 @change="handleChange"
                 :format="renderField.format || 'HH:mm'" allowClear></a-time-picker>

  <!-- 高阶组件 -->
  <!-- treeSelect -->
  <a-tree-select v-else-if="isType('treeSelect')" v-bind="renderField" :value="modelValue" @change="handleChange" allowClear/>
  <!-- transfer -->
  <a-transfer v-else-if="isType('transfer')"
              v-bind="renderField"
              :render="renderField.render || (item => item[renderField.replaceFields.title])"
              :targetKeys="modelValue" @change="handleChange"/>
  <!-- slider -->
  <a-slider v-else-if="isType('slider')" v-bind="renderField" :range="renderField.range || false" :value="modelValue" @change="handleChange"/>
  <!-- autoComplete TODO 自定义option展示形式 -->
  <a-auto-complete v-else-if="isType('autoComplete')"
                   v-bind="renderField"
                   :value="modelValue"
                   @change="handleRemoteSelect"
                   @search="handleRemoteSearch"></a-auto-complete>
  <!-- mentions TODO 自定义option展示形式 -->
  <a-mentions v-else-if="isType('mentions')"
              v-bind="renderField"
              :value="modelValue"
              :filterOption="initFilterOption"
              @search="handleRemoteSearch"
              @change="handleChange">
    <template v-if="renderField.options">
      <a-mentions-option
          v-for="option in renderField.options"
          :key="option[renderField.replaceFields.value]"
          :value="option[renderField.replaceFields.value]">
        {{ option[renderField.replaceFields.title] }}
      </a-mentions-option>
    </template>
  </a-mentions>
  <!-- rate -->
  <a-rate v-else-if="isType('rate')" v-bind="renderField" :value="modelValue" :filterOption="false" @change="handleChange" allowClear/>
  <!-- inputGroup 采用group包裹内部field -->
  <a-input-group v-else-if="isType('inputGroup')"
                 v-bind="renderField"
                 compact>
    <slot name="group"></slot>
  </a-input-group>

  <!-- 自定义组件 -->
  <!-- fileUpload 文件上传 -->
  <FileUpload v-else-if="isType('fileUpload')" v-bind="renderField" :value="modelValue"/>
  <!-- iconPicker -->
  <IconPicker v-else-if="isType('iconPicker')" v-bind="renderField" :modelValue="modelValue" @change="handleChange"/>
  <!-- iconRadio -->
  <IconRadio v-else-if="isType('iconRadio')" v-bind="renderField" :modelValue="modelValue" @change="handleChange"/>
  <!-- imagePicker -->
  <ImagePicker v-else-if="isType('imagePicker')" v-bind="renderField" :value="modelValue"/>
  <!-- mapPicker -->
  <MapPicker v-else-if="isType('mapPicker')" v-bind="renderField" :value="modelValue"/>
  <!-- tableSelect -->
  <TableSelect v-else-if="isType('tableSelect')" v-bind="renderField" :value="modelValue"/>
  <!-- tagCheck -->
  <TagCheck v-else-if="isType('tagCheck')" v-bind="renderField" :value="modelValue"/>

  <!-- 未定义默认组件 -->
  <a-input v-else :value="modelValue" @change="handleInputChange" v-bind="renderField" allowClear/>
</template>

<script>
// 默认替换key
import { FileUpload, IconPicker, IconRadio, ImagePicker, MapPicker, TableSelect, TagCheck } from '@/components/Common/FuncForm/Item'
const defaultKeys = { key: 'key', title: 'title', children: 'children', label: 'label', value: 'value', status: 'status', color: 'color' }
/**
 * Form表单字段渲染
 */
export default {
  name: 'FieldRender',
  components: {
    FileUpload, IconPicker, IconRadio, ImagePicker, MapPicker, TableSelect, TagCheck
  },
  // 防止v-bind绑定时继承多个onChange等异常现象
  inheritAttrs: false,
  props: {
    /**
     * formItem属性定义
     * type: String 控件类型
     *    基础类组件：[text, number, textarea, select|multiple|tags|combobox|remoteSelect, radio, radioButton, cascader, checkbox, switch, treeSelect]
     *    时间类组件：[datePicker, monthPicker, rangePicker, weekPicker, timePicker]
     *    高阶类组件：[transfer, slider, autoComplete, mentions, rate, inputGroup]
     *    自定义组件：[fileUpload, iconPicker, iconRadio, imagePicker, mapPicker, tableSelect, tagCheck]
     *    默认不填写时是text
     *    inputGroup 内部为field对象，属性一致，采用group包裹内部fields
     * label: String 控件label
     * name: String formItem对应的属性key
     * placeholder: String 空值是提示文本，可选，默认根据label构建
     * default: Any 控件默认值，不填写时，下拉选择默认为请选择[-1]，输入框为空，moment为null
     * options: [{ label, value, [children], [disabled] }] 选择控件的下拉菜单，[children]可选属性，级联选择时有效，[disabled]可选
     * rules: [String, Object] String类型为内置校验规则，支持详情见FieldRules.js，Object则为自定义校验，可以混合使用
     * slot: String 自定义表单组件的挂载点名称
     * remote: 时外部传入数据查询和选中回调的方法{ search: function promise方法, select: function }
     * 以及其他各组件支持的属性
     */
    field: {
      type: Object,
      default: () => ({})
    },
    /**
     * 表单属性的绑定值
     */
    modelValue: {
      required: false
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
      // 加载状态
      loading: false
    }
  },
  computed: {
    // 构建用于渲染的field
    renderField () {
      return Object.assign(this.field, {
        placeholder: this.initPlaceholder(),
        size: this.size,
        hidden: this.field.hidden ? this.field.hidden : false,
        replaceFields: Object.assign({}, defaultKeys, this.field.replaceFields),
        // slider不能设置为100%
        style: this.field.style || { width: this.field.type === 'slider' ? '96%' : '100%' }
      })
    }
  },
  emits: ['update:modelValue'],
  methods: {
    // 判断field类型
    isType (type = 'text') {
      return type === (this.renderField.type || 'text')
    },
    // 初始化field空值提示文本
    initPlaceholder () {
      let placeholder = this.field.placeholder
      // TODO 如果inputGroup时需要单独处理内部placeholder
      if (placeholder) {
        return placeholder
      } else if (this.field.type === 'rangePicker') {
        return ['开始日期', '结束日期']
      } else {
        placeholder = this.$utils.isValid(placeholderText[this.field.type]) ? placeholderText[this.field.type] : placeholderText.default
        return placeholder + this.field.label
      }
    },
    // 初始化item mode属性
    initMode (type, field) {
      if (type === 'rangePicker') {
        if (this.$utils.isArray(field.mode)) {
          return field.mode
        } else {
          return [field.mode, field.mode]
        }
      } else {
        return field.mode
      }
    },
    // 初始化选项过滤
    initFilterOption (keyword, option) {
      return this.renderField.remote ? true : option.value.toLowerCase().includes(keyword.toLowerCase())
    },
    // 格式化status
    formatStatus (value) {
      if (this.$utils.isInt(value) || this.$utils.isBoolean(value)) {
        return value ? 'processing' : 'warning'
      } else {
        return value || 'default'
      }
    },
    // 响应远程搜索，从外部传入方法，使用内部解析存在数据流不好上移的问题
    handleRemoteSearch (keyword = this.modelValue) {
      // 存在远程搜索时执行
      if (this.field.remote && this.field.remote.search && keyword) {
        this.renderField.options = null
        if (!this.loading) {
          const self = this
          // 增加延迟请求防止多次无用请求
          setTimeout(() => {
            self.loading = true
            // 调用外部传入的方法
            self.field.remote.search(keyword).then(options => {
              self.renderField.options = options
            }).finally(() => {
              self.loading = false
            })
          }, 300)
        }
      }
    },
    // 响应远程搜索的数据回调
    handleRemoteSelect (value, option) {
      // 如果外部传入的select,则调用外部实现自定义绑定数据
      if (this.field.remote && this.field.remote.select) {
        this.handleChange(value)
        this.field.remote.select(option)
      }
    },
    // 响应input改变
    handleInputChange (event) {
      this.handleChange(event.target.value)
    },
    // 响应radio改变
    handleRadioChange (event) {
      this.handleChange(event.target.value)
    },
    // 响应formItem的值变化，用于双绑
    handleChange (value) {
      this.$emit('update:modelValue', value)
    }
  }
}
/**
 * 内置placeholderText
 */
const placeholderText = {
  text: '请输入',
  textarea: '请输入',
  select: '请选择',
  number: '请输入',
  radio: '请选择',
  cascader: '请选择',
  checkbox: '请选择',
  switch: '请选择',
  treeSelect: '请选择',
  datePicker: '请选择',
  monthPicker: '请选择',
  RangePicker: '请选择',
  weekPicker: '请选择',
  timePicker: '请选择',
  transfer: '请选择',
  slider: '请拖动',
  autoComplete: '请输入',
  mentions: '请输入',
  rate: '请选择',
  inputGroup: '',
  fieldPreview: '',
  fileUpload: '请选择',
  iconPicker: '请选择',
  iconRadio: '请选择',
  tagCheck: '请选择',
  default: '请输入'
}
</script>
