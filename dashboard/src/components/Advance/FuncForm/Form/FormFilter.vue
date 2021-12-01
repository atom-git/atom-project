<template>
  <a-card class="atom-form filter" v-if="fields" :bordered="false">
    <a-form ref="funcForm"
            :model="model"
            :layout="layout"
            :labelAlign="labelAlign"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol">
      <a-row :gutter="32">
        <!-- 过滤条件列表 -->
        <a-col v-bind="colSpan" v-for="field in renderFields" :key="field.name">
          <!-- FormItem渲染 -->
          <a-form-item :name="field.name"
                       :label="field.label"
                       :rules="initRules(field)">
            <!-- 根据field的slot名称设置挂载点，field采用slot方式挂载时，优先级最高 -->
            <slot v-if="field.slot" :name="field.slot" :field="field" :model="model"></slot>
            <!-- inputGroup字段 -->
            <FieldRender v-else-if="field.type === 'inputGroup'"
                         :field="field"
                         :size="field.size || size">
              <template #group>
                <FieldRender v-for="groupField in field.group"
                             :key="groupField.name"
                             :field="groupField"
                             :size="groupField.size || size"
                             v-model="model[groupField.name]"></FieldRender>
              </template>
            </FieldRender>
            <!-- 非inputGroup字段 -->
            <FieldRender v-else :field="field" :size="size" v-model="model[field.name]"></FieldRender>
          </a-form-item>
        </a-col>
        <!-- 查询重置按钮 -->
        <a-col v-bind="buttonSpan">
          <a-form-item :wrapperCol="{ span: 24 }" class="atom-form-footer filter">
            <a-button type="primary" @click="handleSubmit">
              <template #icon><IconFont type="SearchOutlined" /></template>查询
            </a-button>
            <a-button @click="handleReset">
              <template #icon><IconFont type="UndoOutlined" /></template>重置
            </a-button>
            <a-button v-if="fields.length > maxCollapsed" type="link" @click="handleCollapse">
              {{ collapsed ? '展开' : '收起' }}
              <IconFont type="DownOutlined" :style="{ transform: `rotate(${collapsed ? '0turn' : '0.5turn' })` }" />
            </a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-card>
</template>

<script>
/**
 * 查询条件的form表单展示
 */
import { FieldRender } from '@/components/Common/Render'
import form from './mixins/form'
export default {
  name: 'FormFilter',
  components: {
    FieldRender
  },
  mixins: [form],
  props: {
    /**
     * formFilter三种都支持，默认inline
     * horizontal: label和content同一行
     * vertical: label和content不同行
     * inline: 所有内容inline，inline模式需要自定义样式
     */
    layout: {
      type: String,
      default: 'horizontal'
    },
    // 查询条件列表分割布局，用于控制查询条件一行几列
    colSpan: {
      type: Object,
      default: () => ({ xs: 24, md: 12, lg: 12, xl: 8, xxl: 8 })
    }
  },
  data () {
    return {
      // 查询条件是否展开
      collapsed: true,
      // 超过多少就显示展开
      maxCollapsed: 2
    }
  },
  computed: {
    // 用于渲染的fields，分为本身长度大于默认展开长度和小于两种情况
    renderFields () {
      const renderFields = this.$utils.deepClone(this.fields)
      renderFields.forEach(field => {
        if (field.rules) { delete field.rules }
        // filter默认label是左对齐的
        field.labelAlign = 'left'
        // filter中的radio采用select形式展现
        if (field.type === 'radio') {
          field.type = 'select'
        }
      })
      if (renderFields && renderFields.length <= this.maxCollapsed) {
        return renderFields
      } else {
        if (this.collapsed) {
          return renderFields.slice(0, this.maxCollapsed)
        } else {
          return renderFields
        }
      }
    },
    // 动态判断查询按钮的位置offset，使其永远位于最右侧
    buttonSpan () {
      const xs = { span: this.colSpan.xs }
      const md = { span: this.colSpan.md }
      const xl = { span: this.colSpan.xl }
      if (this.fields.length < this.maxCollapsed) {
        xl.offset = (this.maxCollapsed - this.fields.length) * this.colSpan.xl
      } else if (this.fields.length === this.maxCollapsed) {
        md.offset = this.colSpan.md
        xl.offset = (this.maxCollapsed - this.fields.length) * this.colSpan.xl
      } else {
        if (this.collapsed) {
          md.offset = this.colSpan.md
        } else {
          md.offset = (this.fields.length % 2 === 0 ? 1 : 0) * this.colSpan.md
          xl.offset = (2 - this.fields.length % 3) * this.colSpan.xl
        }
      }
      // 如果不需要offset一定要置0，否则会出现异常
      xs.offset = xs.offset || 0
      md.offset = md.offset || 0
      xl.offset = xl.offset || 0
      return { xs: xs, md: md, xl: xl }
    }
  },
  methods: {
    // 响应提交
    handleSubmit () {
      this.submitForm()
    },
    // 响应重置
    handleReset () {
      this.resetForm()
    },
    // 响应展开收起
    handleCollapse () {
      this.collapsed = !this.collapsed
    }
  }
}
</script>

<style lang="less">
@import "Form";
</style>
