<template>
  <span class="atom-json-break">{</span>
  <template v-if="expand">
    <JsonNode v-for="(value, key) in jsonData"
              :key="key"
              :keyName="key"
              :jsonData="value"
              :level="level + 1"
              :expandDepth="expandDepth"
              :sort="sort"
              :timeFormat="timeFormat"></JsonNode>
  </template>
  <span v-else class="atom-json-ellipsis" @click="handleExpandNode">...</span>
  <span class="atom-json-break end">}</span>
</template>

<script>
/**
 * 对象类型Json
 */
import { defineAsyncComponent } from 'vue'
const JsonNode = defineAsyncComponent(() => import('./JsonNode'))
export default {
  name: 'JsonObject',
  components: { JsonNode },
  props: {
    // json数据
    modelValue: {
      type: Object,
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
  data () {
    return {
      // json格式化数据，根据展开层级、是否排序及是否对象，数组数据类型进行展开字段的补充
      jsonData: {}
    }
  },
  computed: {
    // 视图参数，用于全局监听其改变而重新初始化视图展示数据
    viewParams () {
      return {
        modelValue: this.modelValue,
        expandDepth: this.expandDepth,
        sort: this.sort
      }
    }
  },
  watch: {
    // 监听参数变化，重新初始化视图数据
    viewParams: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.jsonData = this.initJson(newValue.modelValue)
      }
    }
  },
  emits: ['json-node-expand'],
  methods: {
    // 初始化json数据
    initJson (jsonData) {
      if (this.sort) {
        const ordered = {}
        Object.keys(jsonData).sort().forEach(key => {
          ordered[key] = jsonData[key]
        })
        return ordered
      } else {
        return this.modelValue
      }
    },
    // 响应节点展开
    handleExpandNode () {
      this.$emit('json-node-expand')
    }
  }
}
</script>
