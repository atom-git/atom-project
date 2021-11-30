<template>
  <FormMaker :style="contentStyle"></FormMaker>
</template>

<script>
/**
 * 动态表单测试示例
 */
import { FormMaker } from '@/components/Advance/FuncForm'
import config from '@/config/mixins/config'
export default {
  name: 'DynamicForm',
  components: { FormMaker },
  mixins: [config]
}
</script>
