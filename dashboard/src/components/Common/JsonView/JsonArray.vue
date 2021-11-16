<template>
  <span class="atom-json-break">[</span>
  <template v-if="expand">
    <JsonNode v-for="(item, index) in jsonData"
              :key="index"
              :jsonData="item"
              :level="level + 1"
              :expandDepth="expandDepth"
              :sort="sort"
              :timeFormat="timeFormat"></JsonNode>
  </template>
  <span v-else class="atom-json-ellipsis" @click="handleExpandNode">...</span>
  <span class="atom-json-break end">]</span>
</template>

<script>
/**
 * 数组类型Json
 */
import { defineAsyncComponent } from 'vue'
const JsonNode = defineAsyncComponent(() => import('./JsonNode'))
export default {
  name: 'JsonArray',
  components: { JsonNode },
  props: {
    // json数据
    jsonData: {
      type: Array,
      required: true
    },
    // 层级
    level: {
      type: Number,
      default: 1
    },
    // 是否排序
    sort: {
      type: Boolean,
      default: false
    },
    // 是否屏开
    expand: {
      type: Boolean,
      default: true
    },
    // 展开层级
    expandDepth: {
      type: Number,
      default: 1
    },
    // 时间格式化
    timeFormat: {
      type: String,
      default: 'YYYY-MM-DD HH24:mi:ss'
    }
  },
  emits: ['json-node-expand'],
  methods: {
    // 响应节点展开
    handleExpandNode () {
      this.$emit('json-node-expand')
    }
  }
}
</script>
