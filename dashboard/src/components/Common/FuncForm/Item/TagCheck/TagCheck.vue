<template>
  <div ref="tag-box" :class="['atom-tag-check', size]">
    <a-checkable-tag v-if="multiple" key="all" v-model:checked="allChecked" @change="handleAllToggle">
      全部
    </a-checkable-tag>
    <a-checkable-tag v-for="(option, index) in tagOptions"
                     v-show="index < showTagLength"
                     :checked="option.checked"
                     :key="option.value"
                     @change="(checked) => handleChange(option, checked)">
      <IconFont v-if="option.icon" :type="option.icon"/>
      {{ option.title }}
    </a-checkable-tag>
    <a-button v-if="maxShowTag !== tagOptions.length" type="link" @click="togglecollapsed">
      {{ collapsed ? '展开' : '收起' }}
      <IconFont type="DownOutlined" :style="{ transform: `rotate(${collapsed ? '0turn' : '0.5turn' })` }" />
    </a-button>
  </div>
</template>

<script>
/**
 * 标签多选器
 */
export default {
  name: 'TagCheck',
  props: {
    // v-model双绑值
    modelValue: {
      type: Array,
      required: false
    },
    // tagcheck option值 [{title, value, icon}]
    options: {
      type: Array,
      default: () => ([])
    },
    // 是否开启多选
    multiple: {
      type: Boolean,
      default: false
    },
    // 尺寸 small | default | large
    size: {
      type: String,
      default: 'default'
    }
  },
  data () {
    return {
      // 默认是收起状态
      collapsed: false,
      // tag选项，针对传入的tag进行checked的初始化
      tagOptions: [],
      // tag盒的宽度
      tagBoxWidth: 0,
      // 能够展示的最大tag数量
      maxShowTag: 0,
      // 选中的tags
      selectedTags: [],
      // 是否全选
      allChecked: false,
      // 是否正在resizing中防止抖动
      resizing: false
    }
  },
  computed: {
    // 显示的tag数量
    showTagLength () {
      if (this.maxShowTag !== this.tagOptions.length && this.collapsed) {
        return this.maxShowTag
      } else {
        return this.tagOptions.length
      }
    }
  },
  mounted () {
    // 挂载时计算tag-box的宽度
    const self = this
    if (self.$refs['tag-box']) {
      self.tagBoxWidth = self.$refs['tag-box'].clientWidth
    }
    self.handleResize()
    window.addEventListener('resize', () => {
      if (self.$refs['tag-box'] && self.$refs['tag-box'].clientWidth) {
        self.tagBoxWidth = self.$refs['tag-box'].clientWidth
        // 防止抖动
        if (!self.resizing) {
          setTimeout(() => {
            self.handleResize()
          }, 500)
        }
      }
    })
  },
  watch: {
    // 监听值的变化
    modelValue () {
      this.initOptions()
    },
    // 监听options的变化
    options: {
      immediate: true,
      deep: true,
      handler () {
        this.initOptions()
      }
    }
  },
  emits: ['update:modelValue', 'change'],
  methods: {
    // 初始化选项，根据传入的modelValue对options的checked进行初始化
    initOptions () {
      this.tagOptions = this.options.map(option => {
        option.checked = this.modelValue && this.modelValue.includes(option.value) || option.checked || false
        return option
      })
      this.selectedTags = this.tagOptions.filter(option => option.checked).map(option => option.value)
      // 如果selectedTags长度等于options的长度，则全选设置为true
      this.allChecked = this.selectedTags.length === this.tagOptions.length
    },
    // 响应窗体尺寸变化
    handleResize () {
      const $tags = this.$refs['tag-box'].children
      let $tagsWidth = 0
      // 能够展示的最大tag数量
      this.maxShowTag = this.tagOptions.length || 0
      for (let i = 0; i < $tags.length; i++) {
        $tagsWidth += $tags[i].offsetWidth + 8
        if ($tagsWidth >= this.tagBoxWidth) {
          this.maxShowTag = i - 2
          this.collapsed = true
          break
        }
      }
      this.resizing = false
    },
    // 响应tag选中状态变化
    handleChange (tag, checked) {
      // 多选时
      if (this.multiple) {
        tag.checked = checked
        // 过滤选中的tags
        this.selectedTags = this.tagOptions.filter(option => option.checked).map(option => option.value)
        // 如果selectedTags长度等于options的长度，则全选设置为true
        this.allChecked = this.selectedTags.length === this.tagOptions.length
      } else {
        // 单选，即使多次点击仍是checked
        tag.checked = true
        this.tagOptions.forEach(option => {
          if (tag.value !== option.value) {
            option.checked = false
          }
        })
        this.selectedTags = [tag.value]
      }
      this.$emit('update:modelValue', this.selectedTags)
      this.$emit('change', this.selectedTags)
    },
    // 响应全选
    handleAllToggle (checked) {
      this.allChecked = checked
      if (this.allChecked) {
        this.tagOptions.forEach(option => option.checked = true)
        this.selectedTags = this.tagOptions.map(option => option.value)
      } else {
        this.tagOptions.forEach(option => option.checked = false)
        this.selectedTags = []
      }
      this.$emit('update:modelValue', this.selectedTags)
      this.$emit('change', this.selectedTags)
    },
    // 响应展开收起
    togglecollapsed () {
      this.collapsed = !this.collapsed
    }
  }
}
</script>

<style lang="less">
@import "tagCheck";
</style>
