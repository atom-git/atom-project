<template>
    <a-list-item>
      <a-card :class="['atom-file', file.status]">
        <a-list-item-meta>
          <!-- 图标 -->
          <template #avatar>
            <a-avatar shape="square" :size="50">
              <template #icon>
                <IconFont :type="file[replaceFields.avatar]"/>
              </template>
            </a-avatar>
          </template>
          <!-- 标题 -->
          <template #title>
            <a-tooltip :title="file[replaceFields.title]">
              {{ file[replaceFields.title] }}
            </a-tooltip>
          </template>
          <!-- 操作按钮 -->
          <!-- 上传中和失败显示其状态 -->
          <template v-if="file.status === 'uploading'" #description>
            <a-progress :percent="file.percent"
                        :status="progress.status"
                        :format="percent => `${Math.round(percent)}%`"
                        size="small" />
          </template>
          <template v-else-if="file.status === 'error'" #description>
            <a-tooltip :title="(file.response && file.response.errorMsg) || ''">
              <a-progress :percent="file.percent"
                          :status="progress.status"
                          :format="percent => `${Math.round(percent)}%`"
                          size="small" />
            </a-tooltip>
          </template>
          <!-- 上传成功显示操作按钮 -->
          <template v-else #description>
            <TipButtonGroup type="icon" :actions="actions" @click="handleAction"></TipButtonGroup>
          </template>
        </a-list-item-meta>
      </a-card>
    </a-list-item>
</template>

<script>
/**
 * 文件组件
 */
import { TipButtonGroup } from '@/components/Common/FuncButton'
import Default from '@/config/default'
export default {
  name: 'File',
  components: { TipButtonGroup },
  props: {
    // 文件
    file: {
      type: Object,
      required: true
    },
    // 文件的操作动作
    actions: {
      type: Array,
      default: () => ([Default.ACTION.PREVIEW, Default.ACTION.DOWNLOAD, Default.ACTION.DELETE])
    },
    // item替换字段
    replaceKeys: {
      type: Object,
      required: false
    }
  },
  computed: {
    // 默认的替换字段
    replaceFields () {
      return Object.assign({ key: 'key', title: 'name', avatar: 'icon', url: 'url' }, this.replaceKeys)
    },
    // 进度条配置
    progress () {
      if (this.file.status === 'uploading') {
        return { status: 'active' }
      } else if (this.file.status === 'error') {
        return { status: 'exception' }
      }  else if (this.file.status === 'done') {
        return { status: 'success' }
      } else {
        return { status: 'normal' }
      }
    }
  },
  methods: {
    // 响应文件操作
    handleAction (action) {
      if (action.name === this.$default.ACTION.PREVIEW.name) {
        // 预览文件
      } else if (action.name === this.$default.ACTION.DOWNLOAD.name) {
        // 下载文件
        this.$api.system.file.download(this.file)
      } else if (action.name === this.$default.ACTION.DELETE.name) {
        // 删除文件
        this.$api.system.file.delete(this.file).then(state => {
          if (state) {
            this.$message.success(`文件【${this.file.name}】删除成功！`)
            this.$emit('file-action', action, this.file)
          } else {
            this.$message.warn(`文件【${this.file.name}】删除失败！`)
          }
        })
      } else {
        // 其他操作
        this.$emit('file-action', action, this.file)
      }
    }
  }
}
</script>

<style lang="less">
@import "file";
</style>
