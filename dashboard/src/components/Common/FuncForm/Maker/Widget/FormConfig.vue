<template>
  <FormList layout="vertical"
            v-model="formConfig"
            :fields="fields"
            @submit="handleSubmit">
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
                      :parser="value => value.replace('px', '')"/>
      <a-input v-else-if="formConfig['labelColType'] === 'grid'"
               v-model:value="model[field.name]"
               :style="{ width: '60%' }"/>
      <!-- getTooltipPopupContainer配置是为了防止切换tab时tooltip不隐藏的问题 -->
      <a-slider v-else
                v-model:value="model[field.name]"
                :max="24"
                :style="{ width: '50%', marginLeft: '12px' }"
                :tooltipVisible="true"
                tooltipPlacement="bottom"
                :getTooltipPopupContainer="triggerNode => triggerNode.parentNode"
                :tipFormatter="value => `{ span: ${value} }`"/>
    </template>
  </FormList>
</template>

<script>
/**
 * 表单配置
 */
import { FormList } from '@/components/Common/FuncForm'
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
      formConfig: {},
      // 表单配置fields
      fields: [
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
              default: 'span',
              style: { width: '40%' },
              // 非slot挂载时，需要按照label来写属性，自动挂载时，会在内部replace
              options: [{ value: 'style', label: '固定宽度' }, { value: 'span', label: '网格布局' }, { value: 'grid', label: '响应布局' }]
            },
            { type: 'text', name: 'labelColSize', default: 6, slot: 'labelColSize' }
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
        },
        {
          type: 'textarea',
          name: 'style',
          label: '自定义样式',
          rows: 4,
          placeholder: '请输入自定义样式[支持less写法]'
        }
      ]
    }
  },
  watch: {
    // 监听外部传入值的变化
    modelValue: {
      deep: true,
      handler (newValue) {
        this.formConfig = newValue
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
        this.formConfig['labelColSize'] = 150
      } else if (labelColType === 'span') {
        this.formConfig['labelColSize'] = 6
      } else if (labelColType === 'grid') {
        this.formConfig['labelColSize'] = '{ xs: 24, sm: 6, md: 8, lg: 8, xl: 6, xxl: 4 }'
      }
    },
    // 响应form提交
    handleSubmit (model) {
      console.log(model)
    }
  }
}
</script>
