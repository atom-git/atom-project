<template>
  <a-list v-if="$utils.isValid(fileList)"
          :grid="{ gutter: 16, xs: 1, sm: 2, md: 3, lg: 3, xl: 3, xxl: 4 }"
          :dataSource="fileList">
    <template #renderItem="{ item, index }">
      <File :key="index"
            :file="item"
            :actions="actions"
            :replaceKeys="replaceKeys"></File>
    </template>
  </a-list>
</template>

<script>
/**
 * 文件列表组件
 */
import File from './File'
export default {
  name: 'FileList',
  components: { File },
  props: {
    // 传入的文件列表
    fileList: {
      type: Array,
      required: true
    },
    // 文件的操作动作
    actions: {
      type: Array,
      required: false
    },
    // item替换字段
    replaceKeys: {
      type: Object,
      required: false
    }
  }
}
</script>
