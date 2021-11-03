<template>
  <editor ref="tinyEditor"
          v-model="content"
          api-key="kle227ufjjqgcf6h7vrdky786yaher37vftpk0w0gtmch1fc"
          :init="editorInit"/>
</template>

<script>
/**
 * tinymce编辑器，TODO 动态初始化
 * tinymce官网帐号密码 atomgit@sina.com / Zronly0622
 * 皮肤配置地址：http://skin.tiny.cloud/t5/?_ga=2.177061413.1296514030.1635736011-257999341.1635736011
 */
import Editor from '@tinymce/tinymce-vue'
import config from '@/config/mixins/config'
const defaultPlugins = [
  'paste print preview searchreplace autolink directionality visualblocks',
  'visualchars fullscreen image media template code codesample',
  'table charmap hr pagebreak nonbreaking anchor insertdatetime advlist',
  'lists wordcount textpattern help emoticons autosave',
  'autoresize ' // quickbars
]
export default {
  name: 'RichText',
  components: { Editor },
  mixins: [config],
  props: {
    // 内容支持双绑
    modelValue: {
      type: String,
      required: false
    },
    // 插件设置
    plugins: {
      type: Array,
      default: () => (defaultPlugins)
    },
    // 是否显示顶部菜单
    menubar: {
      type: Boolean,
      default: false
    },
    // 工具栏是否固定
    toolbarSticky: {
      type: Boolean,
      default: true
    },
    // 菜单超出的呈现形式wrap换行，floating 浮动在下方，sliding ...隐藏，scrolling 横向滚动
    toolbarMode: {
      type: String,
      default: 'wrap'
    },
    // 最小高度
    minHeight: {
      type: Number,
      default: 800
    }
  },
  data () {
    return {
      // 定义富文本编辑器对象
      tinyEditor: null,
      // 编辑器内容
      content: ''
    }
  },
  computed: {
    // tinymce设置
    editorInit () {
      return {
        // 挂载点
        selector: '#atomTiny',
        // 初始化完成的回调方法
        init_instance_callback: (editor) => { this.tinyEditor = editor },
        // 皮肤包
        skin_url: this.publicPath + 'tinymce/skins/ui/atom-tiny',
        skin: 'atom-tiny',
        content_css: this.publicPath + 'tinymce/skins/content/atom-tiny/content.css',
        // 是否显示语言
        language: 'zh_CN',
        language_url: this.publicPath + 'tinymce/langs/zh_CN.js',
        font_formats: '宋体=宋体;黑体=黑体;仿宋=仿宋;微软雅黑=微软雅黑;楷体-GB2312=楷体-GB2312;Verdana=Verdana,Arial,Helvetica Neue,Helvetica,sans-serif;Arial=arial,helvetica,sans-serif; Courier New=courier new,courier,monospace; AkrutiKndPadmini=Akpdmi-n;',
        // 是否显示品牌
        branding: false,
        // 最小高度
        min_height: this.minHeight,
        // 是否显示上部类windows菜单栏
        menubar: this.menubar,
        // 不自动上传
        automatic_uploads: true,
        // 文件上传自定义实现
        images_upload_handler: this.handleImagesUpload,
        // 是否开启高级功能
        image_advtab: true,
        // 图片工具要求图片可跨域
        imagetools_cors_hosts: ['jnanss.com'],
        imagetools_credentials_hosts: ['jnanss.com'],
        // 防止写成相对路径，导致图片加载不出来
        relative_urls: false,
        // 是否支持拖动上传
        block_unsupported_drop: true,
        // 可以选择的文件类型
        file_picker_types: 'image',
        // 允许上传的文件类型
        images_file_types: 'jpeg,jpg,jpe,jfi,jif,jfif,png,gif,bmp,webp',
        // external_plugins: {
        //   imagetools: '@/assets/tiny/plugins/imagetools/plugin.min.js'
        // },
        // export, formatpainter, checklist 为付费组件，media没有必要，自有imagetools 存在图片跨域问题，暂未解决，外部引入的 imagetools 存在翻转等动作后图片无法保存的问题，暂时先放下
        // 图片这块可以考虑新的思路把图片全部放到提交时再保存
        // link去除
        plugins: this.plugins,
        paste_preprocess: (plugin, args) => {
          args.content = args.content.replace(/<\/?a.*?>/g, '')
        },
        // 工具栏固定
        toolbar_sticky: this.toolbarSticky,
        // 菜单超出的呈现形式wrap换行，Floating 浮动在下方，Sliding ...隐藏，Scrolling 横向滚动
        toolbar_mode: this.toolbarMode,
        // 自定义工具组
        toolbar_group: {
          align: {
            icon: 'alignleft',
            tooltip: '对齐',
            items: 'alignleft aligncenter alignright alignjustify'
          }
        },
        // 工具条
        toolbar: 'undo redo | bold italic forecolor backcolor underline strikethrough lineheight | fontselect fontsizeselect formatselect | ' +
            'alignleft aligncenter alignright alignjustify outdent indent | numlist bullist | print preview fullscreen restoredraft | ' +
            'cut copy paste pastetext removeformat | code blockquote anchor subscript superscript table image media | ' +
            'charmap emoticons hr pagebreak insertdatetime',
        // 禁用输入区域快捷工具条
        quickbars_selection_toolbar: false
        // 输入区域快捷工具条
        // quickbars_selection_toolbar: 'bold italic | link h2 h3  | blockquote quickimage quicktable | align'
      }
    }
  },
  watch: {
    // 监听外部传值的变化,此种方式无法回显数据，猜测可能是由于所有的编辑器创建时间过长导致的
    modelValue: {
      handler (val) {
        this.content = val
      }
    },
    // 监听内部值的变化
    content (newValue) {
      this.$emit('update:modelValue', newValue)
      this.$emit('change', newValue)
    }
  },
  methods: {
    // 文件上传自定义实现，这里目前不开启，将图片转成base64的形式进行展示
    handleImagesUpload (fileBlob, success, failure) {
      const file = new File([fileBlob.blob()], fileBlob.filename())
      if (file.size > 350 * 1024) {
        // this.$message.error('图片大小不能超过350KB')
        failure('文件上传失败: 图片大小不能超过350KB')
      } else {
        this.$api.system.file.uploadRichText(file).then(uploadResult => {
          success(uploadResult.url)
        }).catch((e) => {
          // this.$message.error('文件上传失败' + e)
          failure('文件上传失败:' + e)
        })
      }
    },
    // 清空内容
    clear () {
      this.content = ''
    }
  }
}
</script>

<style lang="less">
@import "richText";
</style>
