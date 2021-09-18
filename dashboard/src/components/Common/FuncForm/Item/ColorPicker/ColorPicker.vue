<template>
  <div :class="['atom-color-picker', size]">
    <!-- compact 模式 -->
    <template v-if="pickType === 'compact'">
      <div class="atom-color-block"
           v-for="(color, index) in palette"
           :key="index"
           :style="{ background: color }"
           @click="handleClick(color)">
        <IconFont v-if="color === checkedColor.hex"
                  type="CheckOutlined"
                  style="color: #FFFFFF;" />
      </div>
    </template>
    <!-- 默认 Sketch 模式 -->
    <a-popover v-else placement="topLeft"
               overlayClassName="atom-color-sketch">
      <template #content>
        <Sketch v-model="checkedColor" :presetColors="palette"></Sketch>
      </template>
      <a-input v-model:value="checkedColor.hex8"
               :size="size">
        <template #addonBefore>
          <IconFont type="DashboardFilled" :style="{ color: checkedColor.hex8 }" />
        </template>
      </a-input>
    </a-popover>
  </div>
</template>

<script>
import { Sketch } from '@ckpack/vue-color'
export default {
  name: 'ColorPicker',
  components: { Sketch },
  props: {
    // v-model绑定值
    modelValue: {
      type: String
    },
    // 可选类型包括 compact, sketch
    pickType: {
      type: String,
      default: 'sketch'
    },
    // 组件大小 large|default|small
    size: {
      type: String,
      default: 'default'
    }
  },
  data () {
    return {
      // 选中的color
      checkedColor: {},
      // 默认的颜色列表
      palette: [...this.$default.colorSet, '#FFFFFF', '#00000000']
    }
  },
  watch: {
    // 监听外部传入值的变化
    modelValue: {
      deep: true,
      immediate: true,
      handler (newValue) {
        this.checkedColor = { hex: newValue }
      }
    },
    // 监听内部值的选中变化
    checkedColor: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', newValue.hex)
        this.$emit('change', newValue.hex)
      }
    }
  },
  emits: ['update:modelValue', 'change'],
  methods: {
    // 响应点击
    handleClick (color) {
      this.checkedColor = { hex: color }
    }
  }
}
</script>

<style lang="less">
  .atom-color-picker {
    clear: both;
    .atom-color-block {
      float: left;
      width: 30px;
      height: 30px;
      margin: 0 12px 12px 0;
      font-weight: 700;
      text-align: center;
      border-radius: 2px;
      cursor: pointer;
      vertical-align: middle;
      line-height: 30px;
    }
    .ant-input-group-addon .anticon {
      font-size: 24px;
      cursor: pointer;
    }
    .atom-color-sketch {
      .ant-popover-inner-content {
        padding: 0;
        .vc-sketch {
          width: 220px;
        }
      }
    }
    &.small {
      .atom-color-block {
        width: 24px;
        height: 24px;
        line-height: 24px;
      }
      .ant-input-group-addon .anticon {
        font-size: 20px;
      }
    }
    &.large {
      .atom-color-block {
        width: 36px;
        height: 36px;
        line-height: 36px;
      }
      .ant-input-group-addon .anticon {
        font-size: 28px;
      }
    }
  }
</style>
