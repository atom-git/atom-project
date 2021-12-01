<template>
  <FormMaker :style="contentStyle"></FormMaker>
</template>

<script>
/**
 * 动态表单测试示例
 */
import FormMaker from '@/components/Advance/FormMaker'
import config from '@/config/mixins/config'
export default {
  name: 'DynamicForm',
  components: { FormMaker },
  mixins: [config]
}
</script>
