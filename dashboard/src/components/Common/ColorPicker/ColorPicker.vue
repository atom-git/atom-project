<template>
  <div class="atom-color-picker">
    <sketch-picker v-if="type === 'sketch'" v-model="checkedColor" :presetColors="extendPalette"></sketch-picker>
    <slider-picker v-else-if="type === 'slider'" v-model="checkedColor"></slider-picker>
    <chrome-picker v-else-if="type === 'chrome'" v-model="checkedColor"></chrome-picker>
    <swatches-picker v-else-if="type === 'swatches'" v-model="checkedColor"></swatches-picker>
    <material-picker v-else-if="type === 'material'" v-model="checkedColor"></material-picker>
    <photoshop-picker v-else-if="type === 'photoshop'" v-model="checkedColor"
                      head="颜色选择器"
                      acceptLabel="确定"
                      cancelLabel="取消"
                      resetLabel="重置"
                      newLabel="新建"
                      currentLabel="当前"></photoshop-picker>
    <grayscale-picker v-else-if="type === 'grayscale'" v-model="checkedColor"></grayscale-picker>
    <!-- 默认 compact -->
    <div v-else
         class="atom-color-block"
         v-for="(color, index) in palette"
         :key="index"
         :style="{ background: color }"
         @click="handleClick(color)">
      <IconFont v-if="color === modelValue" type="CheckOutlined" style="color: #FFFFFF;" />
    </div>
  </div>
</template>

<script>
import { Sketch, Slider, Chrome, Swatches, Photoshop, Material } from 'vue-color'
export default {
  name: 'ColorPicker',
  props: {
    // v-model绑定值
    modelValue: {
      type: String
    },
    // 可选类型包括 compact, sketch, slider, chrome, swatches, photoshop, material
    type: {
      type: String,
      default: 'compact'
    }
  },
  components: {
    'sketch-picker': Sketch,
    'slider-picker': Slider,
    'chrome-picker': Chrome,
    'swatches-picker': Swatches,
    'material-picker': Material,
    'photoshop-picker': Photoshop,
    'grayscale-picker': Material
  },
  data () {
    return {
      // 选中的color
      checkedColor: { hex: this.modelValue },
      // 默认的颜色列表
      palette: this.$default.colorSet,
      // 扩展的颜色列表
      extendPalette: this.$default.extendColorSet
    }
  },
  emits: ['update:modelValue', 'change'],
  methods: {
    // 响应点击
    handleClick (color) {
      this.checkedColor.hex = color
      this.$emit('update:modelValue', color)
      this.$emit('change', color)
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
    .vc-material {
      width: 130px;
      height: 130px;
    }
    .vc-slider {
      width: 100%;
    }
  }
</style>
