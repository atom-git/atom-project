<template>
  <div class="atom-json-view">
    <JsonNode :jsonData="jsonData"
              :level="level"
              :expandDepth="expandDepth"
              :sort="sort"
              :timeFormat="timeFormat"></JsonNode>
  </div>
</template>

<script>
/**
 * Json可视化，支持对象，数组的可视化
 * 支持的值类型：对象，数组，布尔，日期，函数，数字，字符串，undefined，null
 */
import { defineAsyncComponent } from 'vue'
const JsonNode = defineAsyncComponent(() => import('./JsonNode'))
export default {
  name: 'JsonView',
  components: { JsonNode },
  props: {
    // 绑定的json data
    modelValue: {
      type: [Object, Array],
      required: true
    },
    // 展开层级
    expandDepth: {
      type: Number,
      default: 1
    },
    // 是否排序
    sort: {
      type: Boolean,
      default: false
    },
    // 时间格式化
    timeFormat: {
      type: String,
      default: 'YYYY-MM-DD HH24:mi:ss'
    }
  },
  data () {
    return {
      // json格式化数据，根据展开层级、是否排序及是否对象，数组数据类型进行展开字段的补充
      jsonData: {},
      // 层级
      level: 1
    }
  },
  watch: {
    modelValue: {
      deep: true,
      immediate: true,
      handler (newValue) {
        if (this.$utils.isObject(newValue) || this.$utils.isArray(newValue)) {
          this.jsonData = this.modelValue
        } else {
          // 提示数据格式问题
          this.$message.error('仅支持数组或者对象类型')
        }
      }
    }
  }
}
</script>

<style lang="less">
@import "jsonView";
</style>
