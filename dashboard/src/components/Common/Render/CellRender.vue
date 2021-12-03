<template>
  <!-- 格式化头像 -->
  <template v-if="isFormat('formatAvatar')">
    <a-avatar v-if="content" :src="content" :style="avatarStyle" alt="A" :loadError="formatAvatarError" />
    <a-avatar v-else :style="avatarStyle" alt="A">
      <template #icon><IconFont type="UserOutlined"/></template>
    </a-avatar>
  </template>
  <!-- 格式化标签 -->
  <template v-else-if="isFormat('formatTag')">
    <a-tag v-if="formatTag && (formatTag().color || formatTag().status)" :color="formatTag && (formatTag().color || formatTag().status)">{{ formatTag && formatTag().title }}</a-tag>
    <a-tag v-else>值缺失</a-tag>
  </template>
  <!-- 格式化状态，无法判断时显示default灰色 -->
  <template v-else-if="isFormat('formatStatus')">
    <a-badge v-if="formatStatus && formatStatus().status" :status="formatStatus().status || 'default'" :text="formatStatus().title" />
    <a-badge v-else-if="formatStatus && formatStatus().color" :color="formatStatus().color || 'cyan'" :text="formatStatus().title" />
    <a-badge v-else status="default" text="值缺失"/>
  </template>
  <!-- 格式化开关，switch -->
  <template v-else-if="isFormat('formatSwitch')">
    <a-switch v-bind="switchOption" :checked="content" @change="handleAction({ name: column.key || column.dataIndex, extend: true })"></a-switch>
  </template>
  <!-- 格式化链接，link -->
  <template v-else-if="isFormat('formatLink')">
    <a-button type="link"
              @click="handleAction({ name: column.key || column.dataIndex, extend: true })">
      <span ref="copy">{{ content }}</span>
      <a-tooltip title="复制"><IconFont @click.stop="handleCopy" type="CopyOutlined" /></a-tooltip>
    </a-button>
  </template>
  <!-- 格式化操作按钮 -->
  <template v-else-if="isFormat('formatAction')">
    <TipButtonGroup :actions="column.actions" @click="handleAction"></TipButtonGroup>
  </template>
  <!-- 格式化长文本提示 -->
  <template v-else-if="isFormat('formatTooltip')">
    <a-tooltip :title="content" placement="topLeft">
      <span>{{ content }}</span>
    </a-tooltip>
  </template>
  <!-- 格式化进度条 -->
  <template v-else-if="isFormat('formatProgress')">
    <div class="atom-format-progress">
      <span :style="progressStyle">{{ content }}</span>
    </div>
  </template>
  <!-- 格式化进度条 -->
  <template v-else-if="isFormat('formatBadge')">
    <a-badge v-bind="formatBadge()" class="atom-format-badge">
      {{ text }}
    </a-badge>
  </template>
  <!-- 其他文本展示内容的格式化 -->
  <template v-else>
    {{ content }}
  </template>
</template>

<script>
/**
 * 格式化单元格
 * 格式化头像: formatAvatar, avatar用于设置头像的属性，属性可以参考avatar组件
 * 格式化标签: formatTag, options { value, title, status }配置tag展示选项，属性可以参考tag组件
 * 格式化状态: formatStatus, options { value, title, status }，应用状态字段更好的展示
 * 格式化开关: formatSwitch, options { value, title, status }，应用状态字段更好的展示，以及直接操作其状态
 * 格式化操作按钮: formatAction|type[icon, text, both], actions [ a-tooltip, a-button ] 属性合集
 * 格式化链接: formatLink展示类似于详情点击
 * 格式化进度条: formatProgress|type[percent, decimal, number]展示成类似于进度条，30% error色，60% warn色 90% info色 100% success色
 * 下面的均为值变化
 * 格式化对象: formatObject|ObjectKey，应用于外键属性渲染
 * 格式化维值: formatType, options，应用于维值渲染 { value, label } 服务端sys_type_mean VO层需要转换下
 * 格式化日期: formatDate|YYYY-MM-DD HH:mm，应用于日期格式化
 * 格式化文本: formatText|{age}岁，应用于文本前后缀，不使用${}是因为es6语法限制，会提示no-template-curly-in-string
 * 字符超长后显示...: formatTooltip
 */
import { TipButtonGroup } from '@/components/Common/TipButton'
import Clipboard from 'clipboard'
export default {
  name: 'CellRender',
  components: { TipButtonGroup },
  props: {
    // 待格式化的列格式
    column: {
      type: Object,
      required: true
    },
    // 字段text文本
    text: {
      type: [String, Number, Array, Object],
      required: false
    },
    // 待格式化的行数据
    row: {
      type: Object,
      required: true
    },
    // 行索引
    index: {
      type: Number,
      required: false
    }
  },
  data () {
    return {
      // 头像样式
      avatarStyle: { backgroundColor: this.$store.getters.primaryColor }
    }
  },
  computed: {
    // 格式化后的内容
    content () {
      return this.initContent()
    },
    // switch的属性
    switchOption () {
      if (this.isFormat('formatSwitch')) {
        return {
          checkedValue: this.$utils.isValid(this.column.form.checkedValue) || 1,
          checkedChildren: this.column.form.checkedChildren || '启',
          unCheckedValue: this.$utils.isValid(this.column.form.checkedValue) || 0,
          unCheckedChildren: this.column.form.checkedChildren || '禁'
        }
      } else {
        return {}
      }
    },
    // 进度条样式
    progressStyle () {
      if (this.isFormat('formatProgress')) {
        return {
          width: this.content,
          backgroundColor: this.content >= '90%' || this.content === '100%' ? '#52C41ACC' : this.content > '60%' ? '#1890FFCC' : this.content >= '30%' ? '#FAAD14CC' : '#F5222DCC'
        }
      } else {
        return {}
      }
    }
  },
  emits: ['cell-action'],
  methods: {
    // 判断是哪种格式化，用于逻辑循环
    isFormat (type) {
      return this.column && this.column.format && this.column.format.indexOf(type) >= 0
    },
    // 生成展示的文本
    initContent () {
      if (this.isFormat('formatAvatar')) {
        return this.formatAvatar()
      } else if (this.isFormat('formatObject')) {
        return this.formatObject()
      } else if (this.isFormat('formatType')) {
        return this.formatType()
      } else if (this.isFormat('formatDate')) {
        return this.formatDate()
      } else if (this.isFormat('formatText')) {
        return this.formatText()
      } else if (this.isFormat('formatProgress')) {
        return this.formatProgress()
      } else {
        return this.text
      }
    },
    // 格式化头像，返回头像url
    formatAvatar () {
      if (this.$utils.isValid(this.text) && this.text.startsWith('http')) {
        return this.text
      }
      // 在链接失效时强制更新
      return undefined
    },
    // 头像文件加载失败的策略
    formatAvatarError () {
      return true
    },
    // 格式化标签
    formatTag () {
      return !this.column.options || this.column.options.filter(option => option.value === this.text)[0] || { color: 'default' }
    },
    // 格式化状态
    formatStatus () {
      return !this.column.options || this.column.options.filter(option => option.value === this.text)[0] || { status: 'default' }
    },
    // 格式化对象
    formatObject () {
      const formater = this.column.format.split('|')
      if (formater.length < 2) {
        return this.text
      } else {
        const keyPath = formater[1]
        let content = this.row
        keyPath.split('.').forEach(key => { content = this.$utils.isValid(content) ? content[key] : '' })
        return content || ''
      }
    },
    // 格式化维值
    formatType () {
      if (this.$utils.isValid(this.column.options)) {
        return this.column.options.filter(option => option.value === this.text)[0].label
      } else {
        return ''
      }
    },
    // 格式化日期
    formatDate () {
      const formater = this.column.format.split('|')
      return this.$utils.formatDate(this.text, formater[1]) || ''
    },
    // 格式化文本
    formatText () {
      const formater = this.column.format.split('|')
      if (formater.length < 2) {
        return ''
      } else {
        const template = formater[1]
        return template.replace(/{(.+?)}/, this.text)
      }
    },
    // 格式化进度条数值
    formatProgress () {
      const formater = this.column.format.split('|')
      if (formater.length < 2) {
        return this.text
      } else {
        // percent, decimal, number
        const template = formater[1]
        if (template === 'percent') {
          // 带百分号
          return this.text
        } else if (template === 'decimal') {
          return (this.text * 100) + '%'
        } else {
          return `${this.text}%`
        }
      }
    },
    // 格式化badge，格式化依据的字段默认为dataIndex，可以通过optionField进行配置
    formatBadge () {
      const option = this.column.options.filter(option => option.value === this.row[this.column['optionField'] || this.column.dataIndex])
      if (this.$utils.isValid(option)) {
        // 因此badge的配置完成由外部决定
        return option[0]
      } else {
        return {}
      }
    },
    // 响应操作按钮的点击
    handleAction (action) {
      this.$emit('cell-action', action, this.row, this.column)
    },
    // link格式化时增加点击copy功能
    handleCopy (event) {
      const clipboard = new Clipboard(this.$refs.copy, { text: () => this.content })
      clipboard.on('success', () => {
        this.$message.success(`【${this.content}】复制成功`)
        // 释放内存
        clipboard.destroy()
      })
      clipboard.on('error', () => {
        this.$notification.warn({
          message: this.content,
          description: '浏览器不支持自动复制，请手动复制'
        })
        // 释放内存
        clipboard.destroy()
      })
      clipboard.onClick(event)
    }
  }
}
</script>

<style lang="less">
.atom-format-badge {
  .ant-badge-count {
    transform: translate(100%, -50%);
  }
}
</style>
