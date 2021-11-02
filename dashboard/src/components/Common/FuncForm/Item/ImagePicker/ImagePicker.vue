<template>
  <!-- 最外面的div必须要，否则会存在modelValue挂载到uploadDragger上导致的warn和错误提示 -->
  <div class="atom-image-picker-container">
    <a-upload-dragger :accept="accept"
                      :showUploadList="false"
                      :openFileDialogOnClick="false"
                      :beforeUpload="handleBeforeUpload">
      <div class="atom-image-picker" :style="{ height: `${height}px` }">
        <a-button-group class="atom-image-picker-actions">
          <a-upload :accept="accept"
                    :showUploadList="false"
                    :beforeUpload="handleBeforeUpload">
            <a-tooltip title="支持拖动上传">
              <a-button><IconFont type="CloudUploadOutlined"/></a-button>
            </a-tooltip>
          </a-upload>
          <a-tooltip title="裁切">
            <a-button @click="handleOperAction('clip')"><IconFont type="ScissorOutlined"/></a-button>
          </a-tooltip>
          <a-dropdown :trigger="['click', 'hover']">
            <a-button>
              <IconFont type="GatewayOutlined"/> {{ `【${fixedText}】` }}
            </a-button>
            <template #overlay>
              <a-menu @click="handleClipAction">
                <a-menu-item key="16:9">16:9</a-menu-item>
                <a-menu-item key="4:3">4:3</a-menu-item>
                <a-menu-item key="1:1">1:1</a-menu-item>
                <a-menu-item key="self">自定义</a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
          <a-tooltip title="放大">
            <a-button @click="handleOperAction('zoomIn')"><IconFont type="ZoomInOutlined"/></a-button>
          </a-tooltip>
          <a-tooltip title="缩小">
            <a-button @click="handleOperAction('zoomOut')"><IconFont type="ZoomOutOutlined"/></a-button>
          </a-tooltip>
          <a-tooltip title="向右旋转">
            <a-button @click="handleOperAction('rotateRight')"><IconFont type="RotateRightOutlined"/></a-button>
          </a-tooltip>
          <a-tooltip title="向左旋转">
            <a-button @click="handleOperAction('rotateLeft')"><IconFont type="RotateLeftOutlined"/></a-button>
          </a-tooltip>
          <a-tooltip title="清除">
            <a-button @click="handleOperAction('delete')"><IconFont type="DeleteOutlined"/></a-button>
          </a-tooltip>
        </a-button-group>
        <!-- 截图 -->
        <VueCropper ref="cropper"
                    :autoCrop="autoCrop"
                    autoCropHeight="1"
                    :img="img"
                    :fixed="fixed"
                    :info="true"
                    :fixedNumber="fixedNumber"
                    :fixedBox="false"
                    @realTime="handleRealTime"></VueCropper>
        <!-- 结果查看 -->
        <div class="atom-image-preview" :style="previewStyle">
          <div :style="previewImg.div">
            <img v-if="previewImg" :src="previewImg.url" :style="previewImg.img" alt="预览图片"/>
          </div>
        </div>
      </div>
    </a-upload-dragger>
  </div>
</template>

<script>
/**
 * 图片选择组件
 */
import { VueCropper } from 'vue-cropper'
import 'vue-cropper/dist/index.css'
export default {
  name: 'ImagePicker',
  components: { VueCropper },
  props: {
    // 外部绑定的图片地址http或者blob或者base64
    modelValue: {
      type: String,
      required: false
    },
    // 高度
    height: {
      type: [String, Number],
      default: 300
    },
    // 裁切比例
    clipRate: {
      type: String,
      default: '1:1'
    },
    // 图片最大的大小，以KB为单位
    maxSize: {
      type: Number,
      defalut: 600
    }
  },
  data () {
    return {
      // 内部图片信息
      img: '',
      // 是否自动生成截图框
      autoCrop: true,
      // 是否固定比例
      fixed: true,
      // 固定的比例
      fixedNumber: [1, 1],
      // 固定比例文字
      fixedText: '1:1',
      // 能够接受的文件类型
      accept: '.jpg,.JPG,.png,.PNG,.jpge,.JPGE',
      // 预览图片
      previewImg: {},
      // 预览窗的样式
      previewStyle: {}
    }
  },
  mounted () {
    // 加载时将这个选项按照外部进行设置
    this.handleClipAction({ key: this.clipRate })
  },
  watch: {
    // 监听外部值的变化
    modelValue (newValue) {
      this.img = newValue
    },
    // 监听内部值的变化
    img (newValue) {
      this.$emit('update:modelValue', newValue)
      this.$emit('change', newValue)
    },
    // 裁切比例变化
    clipRate (newValue) {
      this.handleClipAction({ newValue })
    }
  },
  methods: {
    // 响应操作
    handleClipAction ({ key }) {
      this.autoCrop = false
      this.fixedText = key
      if (key === '16:9') {
        this.fixedNumber = [16, 9]
        this.fixed = true
      } else if (key === '4:3') {
        this.fixedNumber = [4, 3]
        this.fixed = true
      } else if (key === '1:1') {
        this.fixedNumber = [1, 1]
        this.fixed = true
      } else if (key === 'self') {
        this.fixed = false
        this.fixedText = '自定义'
      }
      // 必须放到渲染完再重新生成下
      this.$nextTick(() => {
        this.autoCrop = true
      })
    },
    // 响应操作按钮
    handleOperAction (oper) {
      if (oper === 'clip') {
        this.$refs.cropper.getCropData(data => {
          this.img = data
          this.$message.success('图片裁切成功')
        })
      } else if (oper === 'zoomIn') {
        this.$refs.cropper.changeScale(1)
      } else if (oper === 'zoomOut') {
        this.$refs.cropper.changeScale(-1)
      } else if (oper === 'rotateRight') {
        this.$refs.cropper.rotateRight()
      } else if (oper === 'rotateLeft') {
        this.$refs.cropper.rotateLeft()
      } else if (oper === 'delete') {
        this.img = ''
        this.previewImg = {}
      }
    },
    // 响应上传前
    handleBeforeUpload (file) {
      if (file.size > this.maxSize * 1024) {
        const units = ['KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB', 'BB']
        let index = 0
        let k = this.maxSize
        if (this.maxSize >= 1024) {
          while (k > 1024) {
            k = k / 1024
            index++
          }
        }
        this.$message.error(`文件不能超过${(k).toFixed(2)}${units[index]}`)
        return false
      }
      this.$utils.fileToBase64(file, img => this.img = img)
      return false
    },
    // 响应实时预览
    handleRealTime (data) {
      this.previewStyle = {
        width: `${data.w}px`,
        height: `${data.h}px`,
        overflow: 'hidden',
        zoom: (this.height * 0.4 / data.h)
      }
      this.previewImg = data
    }
  }
}
</script>

<style lang="less">
.atom-image-picker-container .ant-upload.ant-upload-drag {
  background: none;
  border: none;
  .ant-upload.ant-upload-btn {
    padding: 0;
  }
  .atom-image-picker {
    position: relative;
    width: 100%;
    .atom-image-picker-actions {
      position: absolute;
      left: 12px;
      z-index: 1;
      opacity: 0.8;
      &:hover {
        opacity: 1;
      }
      .ant-upload {
        padding: 0;
      }
    }
    .atom-image-preview {
      position: absolute;
      right: 0;
      bottom: 0;
      background: #0000004d;
      width: calc(var(--height) * 0.5);
      img {
        width: 100%;
        height: auto;
      }
    }
  }
}
</style>
