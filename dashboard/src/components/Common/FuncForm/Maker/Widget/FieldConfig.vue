<template>
  <div v-if="options"></div>
  <a-empty v-else description="请添加字段"/>
</template>

<script>
/**
 * 字段配置
 */
export default {
  name: 'FieldConfig',
  props: {
    // 组件配置属性
    options: {
      type: Array,
      required: false
    }
  }
}
</script>
