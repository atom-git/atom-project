<template>
  <div :class="['atom-json-node', dataType]">
  <!-- 展开图标 -->
  <IconFont v-if="isType('object') || isType('array')"
            :type="expand ? 'CaretDownOutlined' : 'CaretRightOutlined'"
            @click="toggleExpand" />
  <span v-if="keyName" class="atom-json-node-key">{{ keyName }}:</span>
  <!-- Json对象 -->
  <JsonObject v-if="isType('object')"
              :modelValue="jsonData"
              :level="level"
              :sort="sort"
              :expand="expand"
              :expandDepth="expandDepth"
              :timeFormat="timeFormat"
              @json-node-expand="toggleExpand"></JsonObject>
  <!-- Json数组 -->
  <JsonArray v-else-if="isType('array')"
             :jsonData="jsonData"
             :level="level"
             :expand="expand"
             :expandDepth="expandDepth"
             :timeFormat="timeFormat"
             @json-node-expand="toggleExpand"></JsonArray>
  <!-- Json基础数据类型-undefined -->
  <span v-else-if="isType('undefined')">undefined</span>
  <!-- Json基础数据类型-null -->
  <span v-else-if="isType('null')">null</span>
  <!-- Json基础数据类型-date -->
  <span v-else-if="isType('date')">"{{ $utils.formatDate(jsonData, timeFormat) }}"</span>
  <!-- Json基础数据类型-function -->
  <a-tooltip v-else-if="isType('function')" :title="jsonData.toString()">
    &lt;function&gt;
  </a-tooltip>
  <!-- Json基础数据类型-number -->
  <span v-else-if="isType('number')">{{ jsonData }}</span>
  <!-- Json基础数据类型-boolean -->
  <span v-else-if="isType('boolean')">{{ jsonData }}</span>
  <!-- Json基础数据类型-string -->
  <span v-else>"{{ jsonData }}"</span>
  </div>
</template>

<script>
/**
 * Json条目
 */
import JsonArray from './JsonArray'
import JsonObject from './JsonObject'
export default {
  name: 'JsonNode',
  components: { JsonArray, JsonObject },
  props: {
    // json数据
    jsonData: {
      required: true
    },
    // key值，不一定有，没有就不写
    keyName: {
      type: String,
      required: false
    },
    // 层级
    level: {
      type: Number,
      default: 1
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
      // 本级是否展开
      expand: false
    }
  },
  computed: {
    // 数据类型
    dataType () {
      return this.$utils.typeIs(this.jsonData)
    }
  },
  mounted () {
    // 当前层级小于等于其最大展开深度时当前级展开
    this.expand = this.level >= this.expandDepth
  },
  methods: {
    // 判断类型
    isType (type) {
      return type === this.dataType
    },
    // 响应展开状态切换
    toggleExpand () {
      this.expand = !this.expand
    }
  }
}
</script>
