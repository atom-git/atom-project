<template>
  <FormList layout="vertical"
            v-model="formConfig"
            :fields="fields"></FormList>
</template>

<script>
/**
 * 表单配置
 */
import { FormList } from '@/components/Common/FuncForm'
export default {
  name: 'FormConfig',
  components: { FormList },
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
      ]
    }
  }
}
</script>
