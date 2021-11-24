<template>
  <FormList ref="formList"
            layout="vertical"
            v-model="formConfig"
            :fields="fields"
            hiddenFooter>
    <!-- labelCol宽度设置挂载点，根据不同的type来展示不同的设置方式 -->
    <template #labelColType="{ field, model }">
      <a-select v-bind="field" v-model:value="model[field.name]" @change="handleLabelChange"></a-select>
    </template>
    <template #labelColSize="{ field, model }">
      <a-input-number v-if="formConfig['labelColType'] === 'style'"
                      v-model:value="model[field.name]"
                      :min="80"
                      :style="{ width: '60%' }"
                      :formatter="value => `${value}px`"
                      :parser="value => value.replace('px', '')"
                      @change="handleLabelColWidth"/>
      <a-input v-else-if="formConfig['labelColType'] === 'grid'"
               v-model:value="model[field.name]"
               :style="{ width: '60%' }"
               @blur="handleLabelColGrid"/>
      <!-- getTooltipPopupContainer配置是为了防止切换tab时tooltip不隐藏的问题 -->
      <a-slider v-else
                v-model:value="model[field.name]"
                :max="24"
                :style="{ width: '50%', marginLeft: '12px' }"
                :tooltipVisible="true"
                tooltipPlacement="bottom"
                :getTooltipPopupContainer="triggerNode => triggerNode.parentNode"
                :tipFormatter="value => `{ span: ${value} }`"
                @change="handleLabelColSpan"/>
    </template>
  </FormList>
</template>

<script>
/**
 * 表单配置
 */
import { FormList } from '@/components/Common/FuncForm'
// 默认表单配置
const defaultForm = {
  width: 100,
  dialogSize: 720,
  labelAlign: 'right',
  labelCol: { style: { width: '80px' } },
  labelColType: 'style',
  labelColSize: '80',
  size: 'default',
  colon: true
}
export default {
  name: 'FormConfig',
  components: { FormList },
  props: {
    // 双绑的form表单配置
    modelValue: {
      type: Object
    }
  },
  data () {
    return {
      // form表单配置
      formConfig: defaultForm,
      // 表单配置fields
      fields: [
        {
          type: 'text',
          name: 'title',
          label: '表单标题',
          rules: [{ required: true }]
        },
        {
          type: 'number',
          name: 'width',
          label: '表单宽度',
          min: 0,
          max: 100,
          default: 100,
          formatter: value => `${value}%`,
          parser: value => value.replace('%', '')
        },
        {
          type: 'number',
          name: 'dialogSize',
          label: '弹窗大小',
          min: 520,
          max: 860,
          default: 720,
          formatter: value => `${value}px`,
          parser: value => value.replace('px', '')
        },
        {
          type: 'radio',
          name: 'labelAlign',
          label: '标签对齐',
          mode: 'button',
          buttonStyle: 'solid',
          default: 'right',
          options: [{ value: 'left', title: '左对齐' }, { value: 'right', title: '右对齐' }, { value: 'vertical', title: '垂直对齐' }]
        },
        {
          type: 'inputGroup',
          name: 'labelCol',
          label: '标签宽度',
          group: [
            {
              type: 'select',
              name: 'labelColType',
              slot: 'labelColType',
              label: '宽度类型',
              default: 'style',
              style: { width: '40%' },
              // 非slot挂载时，需要按照label来写属性，自动挂载时，会在内部replace
              options: [{ value: 'style', label: '固定宽度' }, { value: 'span', label: '网格布局' }, { value: 'grid', label: '响应布局' }]
            },
            { type: 'text', name: 'labelColSize', default: 80, slot: 'labelColSize' }
          ]
        },
        {
          type: 'radio',
          name: 'size',
          label: '组件大小',
          mode: 'button',
          buttonStyle: 'solid',
          default: 'default',
          options: [{ value: 'small', title: '较小' }, { value: 'default', title: '默认' }, { value: 'large', title: '较大' }]
        },
        {
          type: 'switch',
          name: 'colon',
          label: '是否显示冒号[仅在水平模式下有效]',
          checkedValue: true,
          unCheckedValue: false,
          default: true
        }
      ]
    }
  },
  watch: {
    // 监听外部传入值的变化
    modelValue: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.formConfig = this.$utils.isValid(newValue) ? newValue : this.$utils.deepClone(defaultForm)
      }
    },
    // form配置的双绑
    formConfig: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
        this.$emit('change', newValue)
      }
    }
  },
  emits: ['update:modelValue', 'change'],
  methods: {
    // 响应label的类型变化
    handleLabelChange (labelColType) {
      if (labelColType === 'style') {
        this.formConfig['labelColSize'] = 80
        this.formConfig['labelCol'] = { style: { width: '80px' } }
      } else if (labelColType === 'span') {
        this.formConfig['labelColSize'] = 3
        this.formConfig['labelCol'] = { span: 3 }
      } else if (labelColType === 'grid') {
        this.formConfig['labelColSize'] = '{"xs":24,"sm":6,"md":8,"lg":6,"xl":3,"xxl":3}'
        this.formConfig['labelCol'] = { xs: 24, sm: 6, md: 8, lg: 6, xl: 3, xxl: 3 }
      }
    },
    // 响应form label定宽
    handleLabelColWidth () {
      this.formConfig.labelCol = { style: { width: `${this.formConfig.labelColSize}px` } }
    },
    // 响应form label响应式布局
    handleLabelColGrid () {
      try {
        this.formConfig.labelCol = JSON.parse(this.formConfig.labelColSize)
      } catch (err) {
        this.$message.warn(`输入格式有误，参考'{"xs":24,"sm":6,"md":8,"lg":4,"xl":3,"xxl":4}'`)
      }
    },
    // 响应form label定宽
    handleLabelColSpan () {
      this.formConfig.labelCol = { span: this.formConfig.labelColSize }
    }
  }
}
</script>
