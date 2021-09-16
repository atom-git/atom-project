<template>
  <FormMaker :style="formMakerStyle"></FormMaker>
</template>

<script>
/**
 * 动态表单测试示例
 */
import { FormMaker } from '@/components/Common/FuncForm'
import config from '@/config/mixins/config'
export default {
  name: 'DynamicForm',
  components: { FormMaker },
  mixins: [config],
  computed: {
    // 可视区域样式
    formMakerStyle () {
      return { height: this.appConfig.layout === 'top' || this.appConfig.layout === 'drawer' ? this.contentHeight + 'px' : '100%' }
    }
  }
}
</script>

<style lang="less">
.atom-form-maker {
  .atom-maker-panel {
    .ant-layout-content {
      margin-bottom: 0;
    }
  }
}
</style>
