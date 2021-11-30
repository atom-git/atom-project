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
                    :multiple="multiple"
                    v-model:fileList="fileList"
                    :beforeUpload="handleBeforeUpload"
                    @change="handleChange"
                    @reject="handleReject">
    <a-tooltip title="点击或者拖入文件上传">
      <IconFont type="CloudUploadOutlined" /><span class="atom-upload-tip">上传文件</span>
    </a-tooltip>
  </a-upload-dragger>
  <FileList :fileList="fileList" @file-action="handleAction"></FileList>
</template>

<script>
/**
 * 文件上传器
 */
import { FileList } from '@/components/Common/FileList'
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
    // 上传的类型，决定了上传文件的类型 file | img | radio | video，如果直接传入一个可传类型的数组，则直接用
    fileType: {
      type: [String, Array],
      default: 'file'
    },
    // 上传列表的内建样式，支持三种基本样式 text, picture 和 picture-card
    listType: {
      type: String,
      default: 'picture-card'
    },
    // 是否支持多文件
    multiple: {
      type: Boolean,
      default: false
    },
    // 最大允许上传的文件数量
    max: {
      type: Number,
      required: false
    }
  },
  data () {
    return {
      // 上传地址
      action: this.$api.system.file.UPLOAD_URL,
      // 当前操作的文件
      curFile: null,
      // 已经上传的文件列表
      fileList: [],
      // 待上传的文件列表，用于上传前判断是否需要取消上传动作
      waitUpload: []
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
      return this.$utils.isArray(this.fileType) ? this.fileType.length <= 0 ? Default.acceptType['file'] : this.fileType
        : Default.acceptType[this.fileType]
    }
  },
  watch: {
    // 监听外部的值变化
    modelValue: {
      immediate: true,
      deep: true,
      handler (newValue) {
        // 对uid进行兼容处理，防止在文件超出时，导致的文件超出上传异常
        newValue&&newValue.forEach(file => { file.uid = file.uid || file.key })
        this.fileList = newValue || []
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
    // 响应文件上传前
    handleBeforeUpload (file) {
      // max指定时，判断是否超出最大上传数量
      if (this.$utils.isInt(this.max) && ((this.fileList.length + this.waitUpload.length) >= this.max)) {
        this.$message.warn(`仅允许最多上传${this.max}个文件`)
        file.status = 'over'
        return false;
      } else {
        // 写入待上传列表
        this.waitUpload.push(file)
        return true;
      }
    },
    // 响应文件上传状态变化
    handleChange ({ file }) {
      if (file.status === 'uploading') {
        file.key = file.uid
        file.icon = Thumb.default
        // 删除等待上传的列表
        this.waitUpload.forEach((curFile, index) => {
          if (curFile.uid === file.uid) {
            this.waitUpload.splice(index, 1)
          }
        })
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
      } else {
        // 删除无法上传的文件
        this.fileList = [...this.fileList.filter(curFile => curFile.status !== 'over')]
      }
    },
    // 响应拖拽文件不符合 accept 类型时的回调
    handleReject () {
      this.$message.warn(`允许上传的文件类型为【${this.acceptType}】`)
    },
    // 响应文件操作
    handleAction (action, file) {
      if (action.name === this.$default.ACTION.DELETE.name) {
        // 删除
        this.fileList.forEach((curFile, index) => {
          if (curFile.key === file.key) {
            this.fileList.splice(index, 1)
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
@import "FileUpload";
</style>
