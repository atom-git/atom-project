<template>
  <FormList :fields="fields" v-model="model" @submit="handleSubmit">
    <template #number>
      <a-input-number
          v-model:value="model.number1"
          :formatter="value => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
          :parser="value => value.replace(/\$\s?|(,*)/g, '')"/>
    </template>
  </FormList>
</template>

<script>
import { FormList } from '@/components/Common/FuncForm'
export default {
  name: 'BasicForm',
  components: { FormList },
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
            console.log(`formatter:[${value}]`)
            // return `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')
            return `${value}元`
          },
          parser: value => {
            console.log(`parser:[${value}]`)
            // return value.replace(/\$\s?|(,*)/g, '')
            return value.replace(/元/g, '')
          }
        },
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
