<template>
  <a-upload-dragger :class="['atom-upload', size]"
                    v-bind="$attrs"
                    type="drag"
                    :action="action"
                    :headers="headers"
                    :data="data"
                    :listType="listType"
                    :accept="acceptType"
                    :showUploadList="false"
                    v-model:fileList="fileList"
                    @change="handleChange"
                    @reject="handleReject">
    <a-tooltip title="点击或者拖入文件上传">
      <IconFont type="CloudUploadOutlined" /><span class="atom-upload-tip">上传文件</span>
    </a-tooltip>
  </a-upload-dragger>
  <FileList :fileList="fileList"></FileList>
</template>

<script>
/**
 * 文件上传器
 */
import { FileList } from '@/components/Common/File'
import Default from '@/config/default'
import Thumb from './thumb'
export default {
  name: 'FileUpload',
  components: { FileList },
  props: {
    // 双绑的值
    modelValue: {
      type: Array,
      required: false
    },
    // 组件大小 large|default|small
    size: {
      type: String,
      default: 'default'
    },
    // 上传的类型，决定了上传文件的类型 file | img | radio | video
    fileType: {
      type: String,
      default: 'file'
    },
    // 接受的文件类型
    accept: {
      type: String,
      required: false
    },
    // 上传列表的内建样式，支持三种基本样式 text, picture 和 picture-card
    listType: {
      type: String,
      default: 'picture-card'
    }
  },
  data () {
    return {
      // 上传地址
      action: this.$api.system.file.UPLOAD_URL,
      // 当前操作的文件
      curFile: null,
      // 已经上传的文件列表
      fileList: []
    }
  },
  computed: {
    // 上传headers信息
    headers () {
      return { 'Access-Token': this.$store.getters.token }
    },
    // 上传所需参数，这里将上当前路由作为文件上传的目录当成参数上传，合并上传文件
    data () {
      return { folder: this.$route.name }
    },
    // 上传接收的文件类型
    acceptType () {
      return this.accept || Default.acceptType[this.fileType]
    }
  },
  watch: {
    // 监听外部的值变化
    modelValue: {
      deep: true,
      handler (newValue) {
        this.fileList = newValue
      }
    },
    // 监听内部文件列表的变化
    fileList: {
      deep: true,
      handler (newValue) {
        this.$emit('update:modelValue', newValue)
        this.$emit('change', newValue)
      }
    }
  },
  emits: ['update:modelValue', 'change'],
  methods: {
    // 响应文件上传状态变化
    handleChange ({ file }) {
      if (file.status === 'uploading') {
        file.key = file.uid
        file.icon = Thumb.default
      } else if (file.status === 'done') {
        // 上传成功后写入相关信息
        if (file.response && file.response.data.success) {
          file.key = file.response.data.key
          file.url = file.response.data.url
          file.fileType = file.response.data.type
          file.icon = Thumb[file.fileType]
          this.curFile = file
          this.fileList.forEach(curFile => {
            if (curFile.uid === file.uid) {
              curFile.key = file.key
              curFile.url = file.url
              curFile.fileType = file.fileType
              curFile.icon = file.icon
            }
          })
        }
      } else if (file.status === 'error') {
        file.key = file.uid
        file.icon = Thumb.default
        this.$message.error(`文件上传失败，原因:【${file.response.errorMsg}】`)
      }
    },
    // 响应文件预览
    handlePreview (file) {
      console.log(file)
      this.curFile = file
    },
    // 响应文件下载
    handleDownload (file) {
      console.log(file)
      this.curFile = file
    },
    // 响应拖拽文件不符合 accept 类型时的回调
    handleReject () {
      this.$message.warn(`允许上传的文件类型为【${this.acceptType}】`)
    },
    // 响应文件删除
    handleRemove (file) {
      console.log(file)
      this.curFile = file
      return new Promise(resolve => {
        resolve(true)
      })
    }
  }
}
</script>

<style lang="less">
@import "fileUpload";
</style>
