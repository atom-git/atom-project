<template>
  <FormFilter :fields="fields" v-model="model" @submit="handleSubmit"></FormFilter>
  <FormList title="表单功能测试" :fields="fields" v-model="model" @submit="handleSubmit">
    <template #number>
      <a-input-number
          v-model:value="model.number1"
          :formatter="value => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
          :parser="value => value.replace(/\$\s?|(,*)/g, '')"/>
    </template>
  </FormList>
</template>

<script>
/**
 * 基础表单示例
 */
import { FormFilter, FormList } from '@/components/Advance/FuncForm'
export default {
  name: 'BasicForm',
  components: { FormFilter, FormList },
  data () {
    return {
      model: { id: 1, number1: 3, number2: 30000 },
      fields: [
        { name: 'input', label: 'input', default: 'abc' },
        { type: 'textarea', name: 'textarea', label: 'textarea' },
        { type: 'number', name: 'number1', label: 'number1', slot: 'number' },
        {
          type: 'number',
          name: 'number2',
          label: 'number2',
          formatter: value => {
            return `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')
          },
          parser: value => {
            return value.replace(/\$\s?|(,*)/g, '')
          }
        },
        { type: 'number', name: 'number3', label: 'number3' },
        { type: 'select', name: 'select', label: 'select', options: [
            { value: '1', title: 'select选项1' }, { value: '2', title: 'select选项2' }, { value: '3', title: 'select选项3' }, { value: '4', title: 'select选项4' }
          ] },
        { type: 'select', name: 'numberSelect', label: 'numberSelect', options: [
            { value: 1, title: 'select选项1' }, { value: 2, title: 'select选项2' }, { value: 3, title: 'select选项3' }, { value: 4, title: 'select选项4' }
          ] },
        { type: 'select', name: 'multiple', label: 'multiple-select', mode: 'multiple', options: [
            { value: 1, title: 'select选项1' }, { value: 2, title: 'select选项2' }, { value: 3, title: 'select选项3' }, { value: 4, title: 'select选项4' }
          ] },
        // tags得到的值是value与输入的特殊值的混合
        { type: 'select', name: 'tags', label: 'tags-select', mode: 'tags', showSearch: true, options: [
            { value: '1', title: 'select选项1' }, { value: '2', title: 'select选项2' }, { value: '3', title: 'select选项3' }, { value: '4', title: 'select选项4' }
          ] },
        { type: 'remoteSelect', name: 'remoteSelect', label: 'remoteSelect', mode: 'multiple',
          remote: {
            search: (keyword) => {
              return new Promise(resolve => {
                resolve([{ value: '1', title: `${keyword}-select选项1` }, { value: '2', title: `${keyword}-select选项2` }, { value: '3', title: `${keyword}-select选项3` }, { value: '4', title: `${keyword}-select选项4` }])
              })
            },
            select: (option) => { console.log(option)}
          }
        },
        { type: 'radio', name: 'radio', label: 'radio', options: [
            { value: '1', title: 'radio选项1' }, { value: '2', title: 'radio选项2' }, { value: '3', title: 'radio选项3' }, { value: '4', title: 'radio选项4' }
          ] },
        { type: 'switch', name: 'switch', label: 'switch' },
        { type: 'cascader', name: 'cascader', label: 'cascader', options: [
            { value: '1', label: 'cascader选项1', children: [{ value: '1-1', label: 'cascader选项1-1' }, { value: '1-2', label: 'cascader选项1-2' }] },
            { value: '2', label: 'cascader选项2', children: [{ value: '2-1', label: 'cascader选项2-1' }, { value: '2-2', label: 'cascader选项2-2' }] },
            { value: '3', label: 'cascader选项3', children: [{ value: '3-1', label: 'cascader选项3-1' }, { value: '3-2', label: 'cascader选项3-2' }, { value: '3-3', label: 'cascader选项3-3' }] },
            { value: '4', label: 'cascader选项4' }
          ] },
        { type: 'checkbox', name: 'checkbox', label: 'checkbox', options: [
            { value: '1', label: 'checkbox选项1' }, { value: '2', label: 'checkbox选项2' }, { value: '3', label: 'checkbox选项3' }, { value: '4', label: 'checkbox选项4' }
          ] },
        { type: 'datePicker', name: 'datePicker', label: 'datePicker' },
        { type: 'monthPicker', name: 'monthPicker', label: 'monthPicker' },
        { type: 'rangePicker', name: 'rangePicker', label: 'rangePicker' },
        { type: 'weekPicker', name: 'weekPicker', label: 'weekPicker' },
        { type: 'timePicker', name: 'timePicker', label: 'timePicker' },
        { type: 'treeSelect', name: 'treeSelect', label: 'treeSelect', treeData: [
            { value: '1', title: 'treeSelect选项1', children: [{ value: '1-1', title: 'treeSelect选项1-1' }, { value: '1-2', title: 'treeSelect选项1-2' }] },
            { value: '2', title: 'treeSelect选项2', children: [{ value: '2-1', title: 'treeSelect选项2-1' }, { value: '2-2', title: 'treeSelect选项2-2' }] },
            { value: '3', title: 'treeSelect选项3', children: [{ value: '3-1', title: 'treeSelect选项3-1' }, { value: '3-2', title: 'treeSelect选项3-2' }, { value: '3-3', title: 'treeSelect选项3-3' }] },
            { value: '4', title: 'treeSelect选项4' }
          ] },
        { type: 'transfer', name: 'transfer', label: 'transfer', titles: ['来源', '目标'], dataSource: [
            { key: '1', title: 'transfer选项1' }, { key: '2', title: 'transfer选项2' }, { key: '3', title: 'transfer选项3' }, { key: '4', title: 'transfer选项4' },
            { key: '5', title: 'transfer选项5' }, { key: '6', title: 'transfer选项6' }, { key: '7', title: 'transfer选项7' }, { key: '8', title: 'transfer选项8' },
            { key: '9', title: 'transfer选项9' }
          ] },
        { type: 'slider', name: 'slider1', label: 'slider1', step: 10, default: 20 },
        { type: 'slider', name: 'slider2', label: 'slider2', default: [5, 20], range: true,
          marks: { 0: '0°C', 26: '26°C', 37: '37°C', 100: { style: { fontWeight: 'bold' }, label: '100°C' }} },
        { type: 'autoComplete', name: 'autoComplete', label: 'autoComplete',
          remote: {
            search: (keyword) => {
              return new Promise(resolve => {
                resolve([{ value: `${keyword}-autoComplete选项1` }, { value: `${keyword}-autoComplete选项2` }, { value: `${keyword}-autoComplete选项3` }, { value: `${keyword}-autoComplete选项4` }])
              })
            },
            select: (option) => { console.log(option)}
          }
        },
        { type: 'mentions', name: 'mentions1', label: 'mentions1',
          remote: {
            search: (keyword) => {
              return new Promise(resolve => {
                resolve([{ value: 'mentions选项1', title: `${keyword}-mentions选项1` }, { value: 'mentions选项2', title: `${keyword}-mentions选项2` }, { value: 'mentions选项3', title: `${keyword}-mentions选项3` }, { value: 'mentions选项4', title: `${keyword}-mentions选项4` }])
              })
            }
          }
        },
        { type: 'mentions', name: 'mentions2', label: 'mentions2',
          options: [
            { value: 'mentions选项1', title: `mentions选项1` }, { value: 'mentions选项2', title: `mentions选项2` }, { value: 'mentions选项3', title: `mentions选项3` }, { value: '4', title: `mentions选项4` }
          ]
        },
        { type: 'rate', name: 'rate', label: 'rate', default: 3 },
        { type: 'inputGroup', name: 'inputGroup', label: 'inputGroup', group: [
            { type: 'text', name: 'groupText', label: 'groupText', style: { width: '70%' } },
            { type: 'select', name: 'groupSelect', label: 'groupSelect', style: { width: '30%' }, options: [{ value: 1, title: 'select选项1' }, { value: 2, title: 'select选项2' }, { value: 3, title: 'select选项3' }, { value: 4, title: 'select选项4' }] },
          ] },
      ]
    }
  },
  methods: {
    // 响应提交
    handleSubmit (model) {
      console.log(model)
    }
  }
}
</script>
